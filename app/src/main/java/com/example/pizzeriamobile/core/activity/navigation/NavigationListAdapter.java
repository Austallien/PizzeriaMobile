package com.example.pizzeriamobile.core.activity.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;

import java.util.List;

public class NavigationListAdapter extends RecyclerView.Adapter<NavigationListAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<State> states;
    private static OnTouchClickListener callback;

    public interface OnTouchClickListener{
        void navigationListItemTouched(String key);
    }

    public static void registerCallback(OnTouchClickListener callback){
        NavigationListAdapter.callback = callback;
    }

    NavigationListAdapter(Context context, List<State> states){
        this.states = states;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_navigation_drawer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        State state = states.get(position);
        holder.imageViewIcon.setImageResource(state.getIconResourceId());
        holder.textViewInscription.setText(state.getInscription());
        holder.textViewKey.setText(state.getKey());
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageViewIcon;
        final TextView textViewInscription;
        final TextView textViewKey;

        ViewHolder(View view){
            super(view);
            imageViewIcon = (ImageView) view.findViewById(R.id.imageViewIcon);
            textViewInscription = (TextView) view.findViewById(R.id.textViewInscription);
            textViewKey = (TextView) view.findViewById(R.id.textViewKey);

            view.setOnClickListener(onClickListener);
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null)
                    callback.navigationListItemTouched(textViewKey.getText().toString());
                else
                    Toast.makeText(view.getContext(), "NavigationActivity have no registered callback", Toast.LENGTH_LONG);
            }
        };
    }

    public static class State {
        private int iconResourceId;
        private String inscription;
        private String key;

        public State(int iconResourceId, String inscription, String key){
            this.iconResourceId = iconResourceId;
            this.inscription = inscription;
            this.key = key;
        }

        public int getIconResourceId() {
            return iconResourceId;
        }

        public String getInscription() {
            return inscription;
        }

        public String getKey() {
            return key;
        }
    }
}
