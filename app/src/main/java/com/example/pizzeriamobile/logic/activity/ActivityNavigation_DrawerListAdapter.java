package com.example.pizzeriamobile.logic.activity;

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
import com.example.pizzeriamobile.activity.ActivityNavigationDrawer.ListItem;

public class ActivityNavigation_DrawerListAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private int resource;
    private ListItem[] data;

    public ActivityNavigation_DrawerListAdapter(@NonNull Context context, int resource, @NonNull ListItem[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View item = LayoutInflater.from(context).inflate(resource, parent, false);

        ImageView icon = (ImageView) item.findViewById(R.id.imageViewIcon);
        icon.setImageResource(data[position].icon);

        TextView textView = (TextView) item.findViewById(R.id.textViewInscription);
        textView.setText(data[position].inscription);

        return item;
    }

}
