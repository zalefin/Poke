package com.example.pokeapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    Thread wait;
    String regiResult = null;
    public void sendRegiRequest(View v) {
        String nameText = ((EditText)findViewById(R.id.nameField)).getText().toString();
        final String args[] = {"register", nameText};
        if(nameText != "") {
            //create pokey thread to register
            Thread t = RequestManager.requestThreadFactory.newThread(new RequestTask("https://poke.zachlef.in/poke/register", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    while(true) {
                        regiResult = RequestManager.requestThreadFactory.getResult();
                        if(regiResult != null) break;
                    }
                    fileManager.writeName(args[1]);
                    fileManager.writeUUID(regiResult);
                    fileManager.updateFile();
                    returnToMain();
                }
            });
            wait.start();
        }

    }

    private void returnToMain() {
        this.finish();
    }
}