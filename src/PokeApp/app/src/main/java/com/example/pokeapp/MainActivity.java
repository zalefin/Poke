package com.example.pokeapp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//Register request
//type in name, ex "wewlad"
//send request to http://zachlef.in/register/name=wewlad, returns UUID

public class MainActivity extends AppCompatActivity {

    private ArrayList<Friend> friendsArray;
    private FriendAdapter adapter;
    private boolean hasRegistered;
    private NotiMan notificationManager;
    private FileMan fileManager;
    private Handler updateHandler;
    private Handler pollHandler;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Init and start update thread after delay
        updateHandler = new Handler();
        updateHandler.postDelayed(new UpdateTask(updateHandler, this), UpdateTask.INTERVAL);

        // Init and start poll thread after delay
        pollHandler = new Handler();
        pollHandler.postDelayed(new PollTask(pollHandler, this), PollTask.INTERVAL);

        //sdk version is needed for getSystemService
        notificationManager = new NotiMan(this);
        fileManager = new FileMan(this);
        //added stuff for networking. Needed in ANY activity that makes requests.
        RequestManager.init(this);

        //checks if user data exists, prompt registration if not
        if(fileManager.getUUID().equals("")){
            this.finish();
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }else{
            hasRegistered = true;
        }

        //sets uuid text to user uuid
        TextView userUUID = (TextView)findViewById(R.id.uuidView);
        userUUID.setText(fileManager.getName() + "\n" + fileManager.getUUID());

        //set up friend list view with friendAdapter and click listeners
        friendsArray = new ArrayList<>();
        adapter = new FriendAdapter(friendsArray, this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(friendClickedHandler);
        listView.setOnItemLongClickListener(friendLongClickHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //tries to update friends array
        updateFriends();
    }

    //handles list view clicks
    private AdapterView.OnItemClickListener friendClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            //pokes UUID of list item
            poke(adapter.getItemUUID(position), "Test Message");
        }
    };

    //handles list view long click
    private AdapterView.OnItemLongClickListener friendLongClickHandler = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            //shows alert dialog asking if you want to delete friend
            showDeleteFriendDialog(adapter.getItemUUID(position));
            //prevents short click from also responding
            return true;
        }
    };

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //Called when poll is pressed
    //Adds a poll request and sets up a listener.
    //This one will need you to parse a JSON result from a string. format is: {"pokes": []}
    public void poll() {
        RequestManager.addPollRequest(fileManager.getUUID(), response -> {
            Log.d("POLL", "response:" + response);
            notificationManager.createNotification();
        });
    }

    //Called when poke is pressed with list item UUID
    //Adds a poke request and sets up a listener.
    //The result for this should just be a confirmation message.
    public void poke(String UUID, String pokeID) {
        if(!hasRegistered) {
            Toast.makeText(this, "User isn't registered.", Toast.LENGTH_LONG).show();
            return;
        }
        RequestManager.addPokeRequest(fileManager.getUUID(), UUID, pokeID, response -> {
            Log.d("POKE", "response:" + response);
            placeHolderPokeMethod(response);
        });
    }

    public void placeHolderPokeMethod(String r){
        Toast.makeText(this, "Poked!" + r, Toast.LENGTH_SHORT).show();
    }

    //Called when update is pressed
    //Adds an update request and sets up a listener.
    //Similar result to poll; JSON array string with {"name": "Jake", "friends": ["uuid","uuid"]}
    public void updateFriends() {
        if(!hasRegistered) {
            Toast.makeText(this, "User isn't registered.", Toast.LENGTH_LONG).show();
            return;
        }
        RequestManager.addUpdateRequest(fileManager.getUUID(), response -> {
            Log.d("Update Friends", "response:" + response);
            try {
                updateFriendsArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    //called from updateFriends
    private void updateFriendsArray(String r) throws JSONException {
        Log.i("tag", r);
        friendsArray.clear();
        JSONObject friendsjson = new JSONObject(r);
        JSONArray friends = friendsjson.getJSONArray("friends");

        for (int i = 0; i < friends.length(); i++){
            JSONArray jsFriendArray = friends.getJSONArray(i);
            Friend friend = new Friend(
                    jsFriendArray.getString(0),
                    jsFriendArray.getString(1));
            friendsArray.add(friend);
            Log.i("Main", "Added: " + friends.getString(i) );
        }

        adapter.notifyDataSetChanged();
    }

    //Called when poke is pressed with test UUID
    public void removeFriend(String UUID) {
        if(!hasRegistered) {
            Toast.makeText(this, "User isn't registered.", Toast.LENGTH_LONG).show();
            return;
        }
        RequestManager.addRemoveFriendRequest(fileManager.getUUID(), UUID, response -> {
            Log.d("Remove", "response:" + response);
            updateFriends();
        });
    }

    //pop up dialog that shows when user holds down on a friend in the list
    private void showDeleteFriendDialog(String UUID){
        final String[] toBeDeleted = {UUID};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this friend?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeFriend(toBeDeleted[0]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //starts add friend activity
    public void addFriend(View v) {
        if(!hasRegistered) {
            Toast.makeText(this, "User isn't registered.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
    }
}