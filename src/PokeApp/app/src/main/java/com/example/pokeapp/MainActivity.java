package com.example.pokeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public FriendAdapter friendAdapter;
    private PokeAdapter pokeAdapter;
    private FileMan fileManager;
    public boolean visible;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileManager = new FileMan(this);

        //sets up toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(fileManager.getName());

        //initializes request manager for networking
        RequestManager.init(this);

        //checks if user data exists, prompt registration if not
        if(fileManager.getUUID().equals("")){
            this.finish();
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }else{
            initHandlers();
            initFriendList();
        }

        // init poke adapter
        pokeAdapter = new PokeAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //tries to update friends array
        updateFriends();
        visible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        visible = false;
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
        //set up friend list view with friendAdapter and click listeners
        Friend.friendsList = new MappedList<>((friend) -> friend.getUUID());
        friendAdapter = new FriendAdapter(Friend.friendsList, this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(friendAdapter);
        listView.setOnItemClickListener(friendClickedHandler);
        listView.setOnItemLongClickListener(friendLongClickHandler);
    }

    //handles list view clicks
    private AdapterView.OnItemClickListener friendClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Friend f = friendAdapter.getItem(position);
            if(!f.hasPokes()){
                showPokeOptions(f);
            }else{
                showReceivedPoke(f);
            }
        }
    };

    //handles list view long click
    private AdapterView.OnItemLongClickListener friendLongClickHandler = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
            Friend friend = friendAdapter.getItem(position);
            if(friend.getUUID() == null){
                return true;
            }
            //shows alert dialog asking if you want to delete friend
            showDeleteFriendDialog(friend);
            //prevents short click from also responding
            return true;
        }
    };

    //Called when poke is pressed with list item UUID
    //Adds a poke request and sets up a listener.
    //The result for this should just be a confirmation message.
    public void poke(Poke poke) {
        String id = Integer.toString(poke.getPokeType().getId());
        RequestManager.addPokeRequest(
                poke.getSenderUUID(),
                poke.getTargetUUID(),
                id, response -> {

            Log.d("POKE", "response:" + response);
            Snackbar.make(findViewById(R.id.main_layout), "Poked " + Friend.friendsList.get(poke.getTargetUUID()).getName(), Snackbar.LENGTH_LONG).show();
        }, error -> {
            if(error.networkResponse==null){
                Snackbar.make(findViewById(R.id.main_layout), "Check your connection.", Snackbar.LENGTH_LONG).show();
            }else if(error.networkResponse.statusCode == 400){
                Snackbar.make(findViewById(R.id.main_layout), "Invalid user, this user may have unfriended you.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    //Called when update is pressed
    //Adds an update request and sets up a listener.
    //Similar result to poll; JSON array string with {"name": "Jake", "friends": ["uuid","uuid"]}
    public void updateFriends() {
        RequestManager.addUpdateRequest(fileManager.getUUID(), response -> {
            Log.d("Update Friends", "response:" + response);
            ArrayList<Friend> updFriends = new ArrayList<>();
            try {
                JSONObject friendJsonObject = new JSONObject(response);
                JSONArray friendsJsonArray = friendJsonObject.getJSONArray("friends");
                for (int i = 0; i < friendsJsonArray.length(); i ++) {
                    JSONArray jsFriend = friendsJsonArray.getJSONArray(i);
                    Friend friend = new Friend(jsFriend.getString(0), jsFriend.getString(1));
                    updFriends.add(friend);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateFriendsArray(updFriends);
        }, error -> {
            if(error.networkResponse==null){
                Snackbar.make(findViewById(R.id.main_layout), "Check your connection.", Snackbar.LENGTH_LONG).show();
            }else if(error.networkResponse.statusCode == 400){
                Snackbar.make(findViewById(R.id.main_layout), "Invalid user.", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    //called from updateFriends
    private void updateFriendsArray(ArrayList<Friend> updatedFriends) {
        MappedList<Friend, String> friendsList = Friend.friendsList; // alias
        //checks to see if there are any new friends
        for (Friend f: updatedFriends) {
            if(friendsList.get(f.getUUID()) == null){
                friendsList.add(f);
                Log.i("Friends", "Adding " + f.getName() + " to friends list.");
            }
        }
        //if user has no friends, display "Add some friends!"
        TextView noFriendsText = (TextView) findViewById(R.id.noFriendsText);
        if(friendsList.isEmpty()) {
            noFriendsText.setText(R.string.add_some_friends);
        }else{
            noFriendsText.setText("");
        }

        // Sort friends list by display name
        friendsList.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        friendAdapter.notifyDataSetChanged();
    }

    //Called when poke is pressed with test UUID
    public void removeFriend(Friend friend) {
        //attempts to remove friend from list. if successful, sends network request to remove
        if(Friend.friendsList.removeByKey(friend.getUUID())){
            RequestManager.addRemoveFriendRequest(fileManager.getUUID(), friend.getUUID(), response -> {
                Log.d("Remove", "response:" + response);
                updateFriends();
            }, error -> {
                if(error.networkResponse==null){
                    Snackbar.make(findViewById(R.id.main_layout), "Check your connection.", Snackbar.LENGTH_LONG).show();
                }else if(error.networkResponse.statusCode == 400){
                    Snackbar.make(findViewById(R.id.main_layout), "Invalid user.", Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    //pop up dialog that shows when user holds down on a friend in the list
    private void showDeleteFriendDialog(Friend friend){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to delete this friend?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeFriend(friend);
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
    private void showPokeOptions(Friend friend){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setAdapter(pokeAdapter, (dialog, pos) -> {
            Poke outPoke = new Poke(this.fileManager.getUUID(), friend.getUUID(), PokeType.fromId(pokeAdapter.getItem(pos).getId()));
            poke(outPoke);
            dialog.dismiss();
        });

        builder.setCancelable(false);
        builder.setTitle("Poke " + friend.getName() + "?");
        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //pop up dialog that shows when user clicks on friend in list
    private void showReceivedPoke(Friend friend){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        Poke currentPoke = friend.getMostRecentPoke();
        View pokeView = pokeAdapter.getView(currentPoke.getPokeType().getId(), null, null );
        builder.setView(pokeView);

        builder.setCancelable(false);
        builder.setTitle("Poke From " + friend.getName() + "!");
        builder.setNegativeButton("Exit", (dialog, id) -> dialog.dismiss());

        if(friend.hasPokes()) {
            builder.setPositiveButton("Next", (dialog, id) -> {
                dialog.dismiss();
                showReceivedPoke(friend);
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();
        friendAdapter.notifyDataSetChanged();
    }

    //pop up dialog that shows app help info
    private void showHelpDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        builder.setCancelable(false);
        builder.setTitle("Help");
        builder.setMessage("\nClick on add friend to scan a friend's QR code or display your own.\n " +
                "\nClick on a friend to send them a poke!\n " +
                "\nIf you have received a poke from a friend, click on them to view.\n " +
                "\nPress and hold on a friend if you wish to remove them.");
        builder.setNegativeButton("Exit", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    //creates options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //handles selected toolbar options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_friend:
                Intent i = new Intent(this, AddFriendActivity.class);
                startActivity(i);
                return true;
            case R.id.help:
                showHelpDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}