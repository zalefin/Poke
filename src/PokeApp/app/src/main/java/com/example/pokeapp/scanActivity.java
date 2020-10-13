package com.example.pokeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scanActivity extends AppCompatActivity {

    //qr code scanner object
    private IntentIntegrator qrScan;
    private String scannedUUID;
    public boolean hasScanned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        if(!this.checkCameraHardware(this) && this.checkCameraPermission()){
            Log.i("tag","No camera found.");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Log.i("tag", result.getContents());
                scannedUUID = result.getContents();
                ((TextView)findViewById(R.id.uuidViewScan)).setText(scannedUUID);
            }
        }
    }

    public void exitButton(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private boolean checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           return true;
        }
        return false;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


}
