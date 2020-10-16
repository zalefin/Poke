package com.example.pokeapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//Register request
//type in name, ex "wewlad"
//send request to http://zachlef.in/register/name=wewlad, returns UUID

public class MainActivity extends AppCompatActivity {

    public boolean hasRegistered = false;
    private NotiMan notificationManager;
    private FileMan fileManager;
    //for networking. needed in ANY activity that makes requests.
    private PokeyMaker p;
    private RequestQueue queue;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sdk version is needed for getSystemService
        notificationManager = new NotiMan(this);
        fileManager = new FileMan(this);
        //added stuff for networking. Needed in ANY activity that makes requests.
        p = new PokeyMaker();
        queue = Volley.newRequestQueue(this);

        //starts register only if there is no user data file
        if(fileManager.getUUID() == null){
            hasRegistered = false;
        }else{
            hasRegistered = true;
        }

        tryRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFriends();
    }

    private void tryRegister(){
        if(!hasRegistered) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
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
            Thread t = p.newThread(new Pokey(queue, p, "https://poke.zachlef.in/poke/poll", args));
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
        notificationManager.createNotification();
    }

    //Called when poke is pressed with test UUID
    //Adds a poke request and sets up a listener.
    //The result for this should just be a confirmation message.
    public void poke(View v) {
        //You need a target UUID for this. Sending it to user "Friend" right now (check admin)
        final String args[] = {"poke", fileManager.getUUID(), "0e33e1c6-d0a3-4155-941e-fd1d357c458d", "message_here"};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = p.newThread(new Pokey(queue, p, "https://poke.zachlef.in/poke/poke", args));
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

    //Called when update is pressed
    //Adds an update request and sets up a listener.
    //Similar result to poll; JSON array string with {"name": "Jake", "friends": ["uuid","uuid"]}
    public void updateFriends() {
        final String args[] = {"update", fileManager.getUUID()};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = p.newThread(new Pokey(queue, p, "https://poke.zachlef.in/poke/update", args));
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

    //starts add friend activity
    public void addFriend(View v) {

            Intent intent = new Intent(this, addFriendActivity.class);
            startActivity(intent);
    }

    //Called when poke is pressed with test UUID
    public void removeFriend(View v) {
        final String args[] = {"friends/delete", fileManager.getUUID(), "0e33e1c6-d0a3-4155-941e-fd1d357c458d"};
        Thread wait; //calls a method once p has a result
        if(!fileManager.getUUID().equals("")) {
            //create pokey thread to register
            Thread t = p.newThread(new Pokey(queue, p, "https://poke.zachlef.in/poke/friends/delete", args));
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
}