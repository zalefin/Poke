package com.example.pokeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class addFriendActivity extends AppCompatActivity{

    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_friend);
        //finds image view and sets image to generated bitmap
        ImageView qrView = (ImageView) findViewById(R.id.qrView);

        fileManager = new FileMan(this);
        Bitmap loadedBmp = createQrCodeFromUUID(fileManager.getUUID());
        //If no valid UUID exists, display no valid UUID
        TextView noValidUUID = (TextView) findViewById(R.id.noQrCode);
        TextView uuidText = (TextView) findViewById(R.id.uuidViewQR);
        if(loadedBmp == null) {
            noValidUUID.setText("No Valid UUID Loaded");
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
    
    public void leaveQR(View v){
        this.finish();
    }

    public void scanQR(View v){
        Intent intent = new Intent(this, scanActivity.class);
        startActivity(intent);
    }
}