package com.example.pokeapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
            fileManager.updateFile();returnToMain();
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