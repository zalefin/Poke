package com.example.pokeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class qrGenActivity extends AppCompatActivity{

    private Bitmap loadedBmp = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);
        //finds image view and sets image to generated bitmap
        ImageView qrView = (ImageView) findViewById(R.id.qrView);
        loadedBmp = createQrCodeFromUUID(getUUID(false));
        //If no valid UUID exists, display no valid UUID
        TextView noValidUUID = (TextView) findViewById(R.id.noQrCode);
        TextView uuidText = (TextView) findViewById(R.id.uuidViewQR);
        uuidText.setText(getUUID(true));
        if(loadedBmp == null) {
            noValidUUID.setText("No Valid UUID Loaded");
        }
        qrView.setImageBitmap(loadedBmp);

    }

    public Bitmap getQRbmp(){
        return loadedBmp;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getUUID(boolean getName){
        String filename = getString(R.string.uuid_file);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        //open file input to get back earlier thing
        try {
            fis = this.openFileInput(filename);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //assuming that worked, build string out of file contents
        if(fis != null && isr != null) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(isr)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                String result = stringBuilder.toString();
                String justUUID = result.substring(result.length()-37,result.length()-1);
                if(getName){
                    return result;
                }
                return justUUID;
            }
        }
        return "";
    }

    public Bitmap createQrCodeFromUUID(String uuid){
        long key = 0;
        long key2 = 0;
        if(uuid.length()!=36){
            return null;
        }
        //creates 2 long values from 128 bit uuid
        //copies 2nd half to key2
        for(int i = 0; i < 18; i++){
            if(uuid.charAt(i) != 45) {
                key2 = key2 << 4;
            }

            if(uuid.charAt(i) == 45 && i!=8 && i!=13) {
                Log.i("tag","Invalid UUID");
                return null;
            }

            if(uuid.charAt(i) < 58 && uuid.charAt(i) > 47){
                key2 += (uuid.charAt(i)-48);
            }else if(uuid.charAt(i) < 71 && uuid.charAt(i) > 64){
                key2 += (uuid.charAt(i)-55);
            }else if(uuid.charAt(i) < 103 && uuid.charAt(i) > 96){
                key2 += (uuid.charAt(i)-87);
            }else if(uuid.charAt(i) != 45){
                //if any character doesn't follow uuid format, return null bitmap
                Log.i("tag","Invalid UUID");
                return null;
            }
        }
        //copies 1st half to key
        for(int i = 18; i < 36; i++){
            if(uuid.charAt(i) != 45) {
                key = key << 4;
            }

            if(uuid.charAt(i) == 45 && i!=18 && i!=23) {
                Log.i("tag","Invalid UUID");
                return null;
            }

            if(uuid.charAt(i) < 58 && uuid.charAt(i) > 47){
                key += (uuid.charAt(i)-48);
            }else if(uuid.charAt(i) < 71 && uuid.charAt(i) > 64){
                key += (uuid.charAt(i)-55);
            }else if(uuid.charAt(i) < 103 && uuid.charAt(i) > 96){
                key += (uuid.charAt(i)-87);
            }else if(uuid.charAt(i) != 45){
                //if any character doesn't follow uuid format, return null bitmap
                Log.i("tag","Invalid UUID");
                return null;
            }
        }


        //do not change, scale factor that is used to decide how far apart each qr dot will be
        int scale = 60;
        //the reset int resets the x offset after each new line of dots is drawn
        int reset = 0;
        //multx represents by how many dot spaces should the column of dots be offset in the x axis
        int multx = 3;
        //multy represents by how many dot spaces should the row of dots be offset in the y axis
        int multy = 1;
        //creates new bitmap from bmp image base, creates paint and canvas which will draw on bitmap
        Paint qrPaint = new Paint();
        Bitmap qrBmp = BitmapFactory.decodeResource(getResources(), R.drawable.qrbackgroundtest);
        qrBmp = qrBmp.copy(Bitmap.Config.ARGB_8888,true);
        qrBmp = Bitmap.createScaledBitmap(qrBmp,1005,1005,true);
        Canvas qrCanvas = new Canvas(qrBmp);
        //for loop which cycles through each bit in the two 64 bit longs and draws a dot when there's a 1 and doesn't when there's a 0
        //the if statements change the positioning of each dot to fit the pattern
        for(int i = 0; i < 244; i++) {
            if ((key & 1) == 1) {
                qrCanvas.drawCircle( (scale*multx) -8 + ((i-reset)*(scale)) , scale*multy, 16, qrPaint);
            }
            if(i==11){ multx--; multy++; reset = 12; }
            if(i==25){ multx--; multy++; reset = 26; }
            if(i==31){i=35;}
            if(i==41){multy++; reset = 42;}
            if(i==44){i=54;}
            if(i==57){multy++; reset = 58;}
            if(i==60){i=70;}
            if(i==73){multy++; reset = 74;}
            if(i==76){i=86;}
            if(i==89){multy++; reset = 90;}
            if(i==91){i=103;}
            if(i==105){multy++; reset = 106;}
            if(i==107){i=119;}
            if(i==121){multy++; reset = 122;}
            if(i==123){i=135;}
            if(i==137){multy++; reset = 138;}
            if(i==139){i=151;}
            if(i==153){multy++; reset = 154;}
            if(i==156){i=166;}
            if(i==169){multy++; reset = 170;}
            if(i==172){i=182;}
            if(i==185){multy++; reset = 186;}
            if(i==188){i=198;}
            if(i==201){multy++; reset = 202;}
            if(i==207){i=211;}
            if(i==217){multx++; multy++; reset = 218;}
            if(i==231){multx++; multy++; reset = 232;}

            //goes to next bit, or switches to the next key
            if(i==121){key = key2;}else{key = key >> 1;}
        }
        return qrBmp;

    }


    public void leaveQR(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}