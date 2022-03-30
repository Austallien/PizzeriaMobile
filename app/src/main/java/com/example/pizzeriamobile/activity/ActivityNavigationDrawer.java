package com.example.pizzeriamobile.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.activity.ActivityNavigation_DrawerListAdapter;

public class ActivityNavigationDrawer extends RelativeLayout {

    private View view;

    public ActivityNavigationDrawer(@NonNull Context context, RelativeLayout parent) {
        super(context);
        initialize(context, parent);
    }

    private void initialize(Context context, RelativeLayout parent){
        view = LayoutInflater.from(context).inflate(R.layout.layout_navigation_content,parent,true);

        initializeView(context, parent);
        initializeSideList(context);
    }

    private void initializeView(Context context, RelativeLayout parent){

    }

    private void initializeSideList(Context context){
        String[] inscriptions = getResources().getStringArray(R.array.navigationMenu);
        ListItem[] data = new ListItem[]{
                new ListItem(R.drawable.ic_profile_24, inscriptions[0]),
                new ListItem(R.drawable.ic_orders_24, inscriptions[1]),
                new ListItem(R.drawable.ic_settings_24, inscriptions[2]),
                new ListItem(R.drawable.ic_sign_out_24, inscriptions[3]),
        };

        ListView list = (ListView)view.findViewById(R.id.drawer_list);
        ActivityNavigation_DrawerListAdapter adapter = new ActivityNavigation_DrawerListAdapter(context, R.layout.navigation_menu_item, data);
        list.setAdapter(adapter);
    }

    public ListView getListView(){
        ListView list = (ListView)view.findViewById(R.id.drawer_list);
        return list;
    }

    public class ListItem{
        public int icon;
        public String inscription;

        public ListItem(int icon, String inscription) {
            this.icon = icon;
            this.inscription = inscription;
        }
    }


}
