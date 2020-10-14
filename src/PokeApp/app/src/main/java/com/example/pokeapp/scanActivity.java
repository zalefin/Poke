package com.example.pokeapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class scanActivity extends AppCompatActivity {

    //qr code scanner object
    private IntentIntegrator qrScan;
    //PokeyMaker for network
    private PokeyMaker p;
    private RequestQueue queue;
    //fileManager for reading user uuid
    FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        //added stuff for networking. Needed in ANY activity that makes requests.
        p = new PokeyMaker();
        queue = Volley.newRequestQueue(this);

        fileManager = new FileMan(this);

        //checks if device has camera and camera permissions, if not will exit activity
        //need to add ask permissions
        if(!checkCameraHardware(this) && checkCameraPermission()){
            Log.i("tag","No camera found.");
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
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, friendsActivity.class);
                startActivity(i);
            } else {
                Log.i("tag", result.getContents());
                addFriendFromUUID(result.getContents());
                Intent i = new Intent(this, addFriendActivity.class);
                startActivity(i);
            }
        }
    }

    public void addFriendFromUUID(String UUID) {
        final String args[] = {"friends/add", fileManager.getUUID(), UUID};
        Thread wait; //calls a method once p has a result
        if(fileManager.getUUID() != "") {
            //create pokey thread to register
            Thread t = p.newThread(new Pokey(queue, p, "https://poke.zachlef.in/poke/friends/add", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    String result;
                    while(true) {
                        result = p.getResult();
                        if(result != null) break;
                    }
                    //IMPORTANT: This is where behavior for requests should be implemented; call a function with "result" as argument.
                    placeholderResult(result);
                }
            });
            wait.start();
        }
    }

    //placeholder function. replace with your endpoint's needs.
    public void placeholderResult(String r) {
        Log.d("RESULT", r);
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
