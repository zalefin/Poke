package com.example.pokeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    //qr code scanner object
    private IntentIntegrator qrScan;
    //fileManager for reading user uuid
    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //added stuff for networking. Needed in ANY activity that makes requests.

        fileManager = new FileMan(this);

        //checks if device has camera and camera permissions, if not will exit activity
        //need to add ask permissions
        if(!checkCameraHardware(this) && checkCameraPermission()){
            Toast.makeText(this, "No Camera Found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        //creates new intent integrator using zxing
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();

    }

    //gets result after qr scanning intent finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //goes back to friends List
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                //logs scanned UUID and sends request for add friend
                Log.i("tag", result.getContents());
                addFriendFromUUID(result.getContents());
                this.finish();
            }
        }
    }

    //sends network request with scanned UUID
    public void addFriendFromUUID(String UUID) {
        RequestManager.addAddFriendRequest(fileManager.getUUID(), UUID, response -> {
            if (response.substring(0, 7).equals("Already")) {
                Toast.makeText(this, "Already Friends!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //placeholder function. replace with your endpoint's needs.
    public void confirmResult(String r) {
        Looper.prepare();
        Log.i("volley", r);
        if(r.substring(0,7).equals("Already")){
            Toast.makeText(this, "Already Friends!", Toast.LENGTH_SHORT).show();
        }
    }

    //checks if app has given camera permission
    private boolean checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           return true;
        }
        return false;
    }

    //checks if phone has a camera
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
