package com.example.pokeapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


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
        return PokeType.values()[pos].getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.poke_list_layout, null);
        }

        PokeType type = PokeType.fromId(position);

        //this is the poke text
        TextView pokeMessage = (TextView)view.findViewById(R.id.poke_text);
        pokeMessage.setText(type.getContent());
        Typeface typeface = ResourcesCompat.getFont(context, type.getFont());
        pokeMessage.setTypeface(typeface);
        pokeMessage.setTextColor(ContextCompat.getColor(context, type.getColor()));
        pokeMessage.setTextSize(TypedValue.COMPLEX_UNIT_DIP, type.getFontSize());

        //this is poke image
        ImageView pokeImage = view.findViewById(R.id.poke_image);
        Drawable image = ContextCompat.getDrawable(context, type.getImageSrc());
        pokeImage.setImageDrawable(image);
        return view;
    }
}
