package com.example.pokeapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class AddFriendActivity extends AppCompatActivity{

    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        fileManager = new FileMan(this);

        //finds image view and sets image to generated bitmap
        ImageView qrView = (ImageView) findViewById(R.id.qrView);
        TextView uuidText = (TextView) findViewById(R.id.uuidViewQR);

        //attempts to read bitmap from file, if none exist returns null
        Bitmap loadedBmp = readBitmapFromFile("qrcode.png");

        //if no qr code is currently saved, generates new qr code
        if(loadedBmp == null) {
            Log.i("QR", "Creating Bitmap");
            Bitmap outBmp = createQrCodeFromUUID(fileManager.getUUID());
            outBmp = changeColor(outBmp, 0x7CFC00);
            writeBitmapToFile("qrcode.png", outBmp);
            //reload page
            this.finish();
            startActivity(this.getIntent());
        }else{
            //gets UUID from fileman
            uuidText.setText(fileManager.getName() + "\n" + fileManager.getUUID());
            qrView.setImageBitmap(loadedBmp);
        }
    }


    //new qr code creation using zxing
    //returns bitmap created from UUID string
    private Bitmap createQrCodeFromUUID(String UUID){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(UUID, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    //changes background color of qr code
    private Bitmap changeColor(Bitmap input, int color){
        if(input == null){
            return null;
        }
        int width = input.getWidth();
        int height = input.getHeight();
        int [] pixels = new int[height * width];
        input.getPixels(pixels, 0, width, 0 ,0, width, height);
        for(int i = 0; i < pixels.length; i++){
            if(pixels[i] == -1){
                pixels[i] = color;
            }
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        output.setPixels(pixels, 0, width,0,0,width, height);
        return output;
    }

    //writes generated qr to file
    private void writeBitmapToFile(String filename, Bitmap outBmp){
        if(outBmp == null){
            return;
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        outBmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        FileOutputStream fos;
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(bytes.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //attempts to read existing qr from file
    private Bitmap readBitmapFromFile(String filename){
        Bitmap outBmp;
        FileInputStream fis;
        try {
            fis = openFileInput(filename);
            outBmp = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return outBmp;
    }

    public void leaveQR(View v){
        this.finish();
    }

    public void scanQR(View v){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }
}