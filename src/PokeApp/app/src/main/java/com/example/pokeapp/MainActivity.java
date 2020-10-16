package com.example.pokeapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


//Register request
//type in name, ex "wewlad"
//send request to http://zachlef.in/register/name=wewlad, returns UUID

public class MainActivity extends AppCompatActivity {

    private NotiMan notificationManager;
    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sdk version is needed for getSystemService
        notificationManager = new NotiMan(this);
        fileManager = new FileMan(this);
        //added stuff for networking. Needed in ANY activity that makes requests.
        RequestManager.init(this);
    }

    //Called when register is pressed
    //for networking: switch to register activity
    public void register(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    //Called when Friends button is pressed
    //opens friends Activity
    public void openFriends(View v) {
        Intent i = new Intent(this, friendsActivity.class);
        startActivity(i);
    }

    /*
    =====NOTIFICATION BRANCH=====
    */

    public void notify(View v) {
        notificationManager.createNotification();
    }


    //#####UNIMPLEMENTED ENDPOINTS START HERE#####

    //Called when poll is pressed
    //Adds a poll request and sets up a listener.
    //This one will need you to parse a JSON result from a string. format is: {"pokes": []}
    public void poll(View v) {
        final String args[] = {"poll", fileManager.getUUID()};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = RequestManager.requestThreadFactory.newThread(new RequestTask("https://poke.zachlef.in/poke/poll", args));
            t.start();
            //create thread to wait for result
            wait = new Thread(new Runnable(){
                @Override
                public void run() {
                    String result;
                    while(true) {
                        result = RequestManager.requestThreadFactory.getResult();
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
}