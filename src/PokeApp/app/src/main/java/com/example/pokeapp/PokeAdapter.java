package com.example.pokeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PokeAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Poke> pokesList;
    private Context context;

    public PokeAdapter(ArrayList<Poke> pokesList, Context context) {
        this.pokesList = pokesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return pokesList.size();
    }

    @Override
    public Object getItem(int pos) {
        return pokesList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    public String getItemSenderUUID(int pos) {
        return pokesList.get(pos).getSenderUUID();
    }

    public String getItemPokeID(int pos) {
        return pokesList.get(pos).getPokeID();
    }

    public String getItemMessage(int pos) {
        return pokesList.get(pos).getMessage();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.poke_list_layout, null);
        }

        //Handle TextView and display friend name/uuid from list
        TextView pokeMessage = (TextView)view.findViewById(R.id.poke_message_text);
        pokeMessage.setText(getItemMessage(position));

        return view;
    }

}
