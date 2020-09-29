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
        createQrCode(1);

    }

    public void createQrCode(int key){
        ImageView qrView = (ImageView)findViewById(R.id.qrView);

        Paint qrPaint = new Paint();
        Bitmap qrBmp = BitmapFactory.decodeResource(getResources(), R.drawable.qrbackgroundtest);
        qrBmp = qrBmp.copy(Bitmap.Config.ARGB_8888,true);
        Canvas qrCanvas = new Canvas(qrBmp);

        if (key > 0) {
            qrCanvas.drawCircle(150,150,50,qrPaint);
        }

        qrView.setImageBitmap(qrBmp);

    }


    public void leaveQR(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}