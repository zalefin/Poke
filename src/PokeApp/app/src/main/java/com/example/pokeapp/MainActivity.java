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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Poke> pokesArray;
    private ArrayList<Friend> friendsArray;
    private FriendAdapter friendAdapter;
    private PokeAdapter pokeAdapter;
    private FileMan fileManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileManager = new FileMan(this);

        //added stuff for networking. Needed in ANY activity that makes requests.
        RequestManager.init(this);

        //checks if user data exists, prompt registration if not
        if(fileManager.getUUID().equals("")){
            this.finish();
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }else{
            initHandlers();
            initFriendList();
            initPokeList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //tries to update friends array
        updateFriends();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initHandlers(){
        // Init and start update thread after delay
        Handler updateHandler = new Handler();
        updateHandler.postDelayed(new UpdateTask(updateHandler, this), UpdateTask.INTERVAL);

        // Init and start poll thread after delay
        Handler pollHandler = new Handler();
        pollHandler.postDelayed(new PollTask(pollHandler, this), PollTask.INTERVAL);
    }

    private void initFriendList(){
        //sets uuid text to user uuid
        TextView userUUID = (TextView)findViewById(R.id.uuidView);
        userUUID.setText(fileManager.getName() + "\n" + fileManager.getUUID());
        //set up friend list view with friendAdapter and click listeners
        friendsArray = new ArrayList<>();
        friendAdapter = new FriendAdapter(friendsArray, this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(friendAdapter);
        listView.setOnItemClickListener(friendClickedHandler);
        listView.setOnItemLongClickListener(friendLongClickHandler);
    }

    private void initPokeList(){
        String userUUID = fileManager.getUUID();
        //set up friend list view with friendAdapter and click listeners
        pokesArray = new ArrayList<>();
        pokesArray.add(new Poke(userUUID, "1", "Message 1"));
        pokesArray.add(new Poke(userUUID, "2", "Message 2"));
        pokesArray.add(new Poke(userUUID, "3", "Message 3"));
        pokeAdapter = new PokeAdapter(pokesArray, this);
    }

    //handles list view clicks
    private AdapterView.OnItemClickListener friendClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            showPokeOptions(friendAdapter.getItemUUID(position),friendAdapter.getItemName(position));
        }
    };

    //handles list view long click
    private AdapterView.OnItemLongClickListener friendLongClickHandler = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            if(friendAdapter.getItemUUID(position) == null){
                return true;
            }
            //shows alert dialog asking if you want to delete friend
            showDeleteFriendDialog(friendAdapter.getItemUUID(position));
            //prevents short click from also responding
            return true;
        }
    };

    //Called when poke is pressed with list item UUID
    //Adds a poke request and sets up a listener.
    //The result for this should just be a confirmation message.
    public void poke(String UUID, String pokeID) {
        if(UUID == null){
            return;
        }
        RequestManager.addPokeRequest(fileManager.getUUID(), UUID, pokeID, response -> {
            Log.d("POKE", "response:" + response);
            Toast.makeText(this, "Poked with " + pokeID, Toast.LENGTH_SHORT).show();
        });
    }

    //Called when update is pressed
    //Adds an update request and sets up a listener.
    //Similar result to poll; JSON array string with {"name": "Jake", "friends": ["uuid","uuid"]}
    public void updateFriends() {
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

        if(friendsArray.isEmpty()){
            Friend friend = new Friend("Add Some Friends!" , null);
            friendsArray.add(friend);
        }

        friendAdapter.notifyDataSetChanged();
    }

    //Called when poke is pressed with test UUID
    public void removeFriend(String UUID) {
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

    //pop up dialog that shows when user clicks on friend in list
    private void showPokeOptions(String targetUUID, String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(pokeAdapter, (dialog, item) -> {
            poke(targetUUID, pokeAdapter.getItemPokeID(item));
            dialog.dismiss();
        });

        builder.setCancelable(false);
        builder.setTitle("Poke " + name + "?");
        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //starts add friend activity
    public void addFriend(View v) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
    }
}