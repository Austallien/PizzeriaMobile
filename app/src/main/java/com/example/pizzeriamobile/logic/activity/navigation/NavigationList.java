package com.example.pizzeriamobile.logic.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.R;

public class NavigationList extends ArrayAdapter<NavigationListItem> {

    private Context context;
    private int resource;
    private NavigationListItem[] data;

    public NavigationList(@NonNull Context context, int resource, @NonNull NavigationListItem[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View item = LayoutInflater.from(context).inflate(resource, parent, false);

        ImageView imageViewIcon = (ImageView) item.findViewById(R.id.imageViewIcon);
        imageViewIcon.setImageResource(data[position].icon);

        TextView textViewInscription = (TextView) item.findViewById(R.id.textViewInscription);
        textViewInscription.setText(data[position].inscription);

        TextView textViewKey = (TextView) item.findViewById(R.id.textViewKey);
        textViewKey.setText(data[position].key);

        return item;
    }

}
