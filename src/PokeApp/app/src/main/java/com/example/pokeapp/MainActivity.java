package com.example.pokeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.lang.*;

import com.example.pokeapp.Pokey;
import com.example.pokeapp.PokeyMaker;

//Register request
//type in name, ex "wewlad"
//send request to http://zachlef.in/register/name=wewlad, returns UUID

public class MainActivity extends AppCompatActivity {
    private Boolean createNewQr = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    //Called when QR is pressed
    //switch to qr activity
    public void openQR(View v) {
        Intent i = new Intent(this, qrGenActivity.class);
        i.putExtra("createNewQr", createNewQr);
        startActivity(i);
    }

    //Called when Dummy1 is pressed
    //for networking: switch to register activity
    public void d1(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    //Called when Dummy2 is pressed
    //for networking: loads UUID from file
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void d2(View v) {
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
                TextView text = (TextView)findViewById(R.id.uuidView);
                text.setText(result);
            }
        }
    }

}