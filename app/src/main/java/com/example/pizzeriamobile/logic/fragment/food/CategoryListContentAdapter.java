package com.example.pizzeriamobile.logic.fragment.food;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.activity.navigation.NavigationListAdapter;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

public class CategoryListContentAdapter extends RecyclerView.Adapter<CategoryListContentAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<State> states;
    private static OnLongTouchListener callback;

    public interface OnLongTouchListener{
        void productListItemTouched();
    }

    public static void registerCallback(OnLongTouchListener callback){
        CategoryListContentAdapter.callback = callback;
    }

    public CategoryListContentAdapter(Context context, List<State> states){
        inflater = LayoutInflater.from(context);
        this.states = states;
    }

    @NonNull
    @Override
    public CategoryListContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_category_content_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListContentAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        holder.textViewProductName.setText(state.getProductName());
        holder.imageViewProductImage.setImageBitmap(state.getProductImage());
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView textViewProductName;
        final ImageView imageViewProductImage;

        public ViewHolder(View view) {
            super(view);
            textViewProductName = (TextView) view.findViewById(R.id.textViewProductName);
            imageViewProductImage = (ImageView) view.findViewById(R.id.imageViewProductImage);

            view.setOnLongClickListener(onLongClickListener);
        }

        private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        };
    }

    public static class State{
        Bitmap productImage;
        String productName;

        public State(Bitmap productImage, String productName){
            this.productImage = productImage;
            this.productName = productName;
        }

        public String getProductName() {
            return productName;
        }

        public Bitmap getProductImage() {
            return productImage;
        }
    }
}