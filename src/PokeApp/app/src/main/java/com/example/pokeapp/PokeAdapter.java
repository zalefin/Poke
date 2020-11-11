package com.example.pokeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


public class PokeAdapter extends BaseAdapter implements ListAdapter {
    private Context context;

    public PokeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return PokeType.values().length;
    }

    @Override
    public PokeType getItem(int pos) {
        return PokeType.values()[pos];
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
            view = inflater.inflate(R.layout.poke_list_layout, null);
        }

        //Handle TextView and display friend name/uuid from list
        TextView pokeMessage = (TextView)view.findViewById(R.id.poke_message_text);
        pokeMessage.setText(PokeType.fromId(position).getContent());

        return view;
    }
}
