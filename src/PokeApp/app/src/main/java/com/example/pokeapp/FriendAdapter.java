package com.example.pokeapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> friendList;
    private Context context;

    public FriendAdapter(ArrayList<String> friendList, Context context) {
        this.friendList = friendList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int pos) {
        return friendList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    public String getItemUUID(int pos) throws JSONException {
        JSONArray friend = new JSONArray(friendList.get(pos));
        return friend.getString(1);
    }

    public String getItemName(int pos) throws JSONException {
        JSONArray friend = new JSONArray(friendList.get(pos));
        return friend.getString(0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.friend_list_layout, null);
        }

        //Handle TextView and display friend name/uuid from list
        TextView friendName = (TextView)view.findViewById(R.id.friend_name_text);
        TextView friendUUID = (TextView)view.findViewById(R.id.friend_uuid_text);
        try {
            friendName.setText(getItemName(position));
            friendUUID.setText(getItemUUID(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}