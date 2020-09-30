package com.example.pokeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileOutputStream;


public class qrGenActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_gen);
        createQrCode(-1,-1);

    }

    public void createQrCode(long key1, long key2){
        ImageView qrView = (ImageView)findViewById(R.id.qrView);
        int scale = 60;
        int reset = 0;
        int multx = 3;
        int multy = 1;
        Paint qrPaint = new Paint();
        Bitmap qrBmp = BitmapFactory.decodeResource(getResources(), R.drawable.qrbackgroundtest);
        qrBmp = qrBmp.copy(Bitmap.Config.ARGB_8888,true);
        qrBmp = Bitmap.createScaledBitmap(qrBmp,1005,1005,true);
        Canvas qrCanvas = new Canvas(qrBmp);

        for(int i = 0; i < 244; i++) {
            if ((key1 & 1) == 1) {
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
            key1 = key1 >> 1;
        }






        qrView.setImageBitmap(qrBmp);

    }


    public void leaveQR(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}