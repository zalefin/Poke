package com.example.pokeapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FriendAdapter extends BaseAdapter implements ListAdapter {

    private int [] color = {R.color.color0, R.color.color1, R.color.color2, R.color.color3,
            R.color.color4, R.color.color5, R.color.color6, R.color.color7,
            R.color.color8, R.color.color9, R.color.color10, R.color.color11,
            R.color.color12, R.color.color13, R.color.color14, R.color.color15};

    private MappedList<Friend, String> friendsList;
    private Context context;

    public FriendAdapter(MappedList<Friend, String> friendsList, Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public Friend getItem(int pos) {
        return friendsList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.friend_list_layout, null);
        }

        Friend friend = getItem(position);

        TextView pokesText = (TextView)view.findViewById(R.id.pokesText);
        String numPokes = "";
        if(friend.hasPokes()){
            if(friend.getNumPokes() == 1){
                numPokes = friend.getNumPokes() + " new Poke!";
            }else{
                numPokes = friend.getNumPokes() + " new Pokes!";
            }
        }
        pokesText.setText(numPokes);

                //Handle TextView and display friend name/uuid from list
        TextView friendName = (TextView)view.findViewById(R.id.friend_name_text);
        //TextView friendUUID = (TextView)view.findViewById(R.id.friend_uuid_text);

        friendName.setText(friend.getName());
        //friendUUID.setText(friend.getUUID());


        //this adds a colorful icon next to each friend based on their uuid
        int index = Character.digit(friend.getUUID().charAt(0), 16);
        FloatingActionButton circle = (FloatingActionButton)view.findViewById(R.id.circle);
        circle.setBackgroundTintList(AppCompatResources.getColorStateList(context, color[index]));

        return view;
    }

}
