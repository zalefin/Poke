package com.example.pokeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fileManager = new FileMan(this);
    }

    public void sendRegiRequest(View v) {
        String nameText = ((EditText)findViewById(R.id.nameField)).getText().toString();
        RequestManager.addRegisterRequest(nameText, response -> {
            Log.i("Register", response);
            fileManager.writeName(nameText);
            fileManager.writeUUID(response);
            fileManager.updateFile();
            returnToMain();
        }, error -> {
            if(error.networkResponse==null){
                new android.app.AlertDialog.Builder(this).setTitle("Check Your Connection")
                        .setMessage("Could not connect to server.")
                        .setPositiveButton("OK", (dialog, id) -> dialog.dismiss()).show();
            }else if(error.networkResponse.statusCode == 400){
                new android.app.AlertDialog.Builder(this).setTitle("Name Too Long!")
                        .setMessage("Name must be 32 characters or less.")
                        .setPositiveButton("OK", (dialog, id) -> dialog.dismiss()).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You must register!", Toast.LENGTH_SHORT).show();
    }

    private void returnToMain() {
        this.finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}