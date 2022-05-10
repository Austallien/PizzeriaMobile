package com.example.pizzeriamobile.logic.fragment.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;

import java.util.ArrayList;

public class CategoryListContentAdapter extends RecyclerView.Adapter<CategoryListContentAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<ContentItem> content;
    private static OnTouchListener callback;

    public interface OnTouchListener {
        void productListItemTouched(int productId);
    }

    public static void registerCallback(OnTouchListener callback){
        CategoryListContentAdapter.callback = callback;
    }

    public CategoryListContentAdapter(Context context, ArrayList<ContentItem> content){
        inflater = LayoutInflater.from(context);
        this.content = content;
    }

    @NonNull
    @Override
    public CategoryListContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_category_child_item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListContentAdapter.ViewHolder holder, int position) {
        ContentItem content = this.content.get(position);
        holder.bind(content);
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final public TextView textViewProductName;
        final public ImageView imageViewProductImage;

        public ViewHolder(View view) {
            super(view);
            textViewProductName = (TextView) view.findViewById(R.id.textViewProductName);
            imageViewProductImage = (ImageView) view.findViewById(R.id.imageViewProductImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    int id = content.get(index).Id;
                    callback.productListItemTouched(id);
                }
            });
        }

        public void bind(ContentItem content){
            textViewProductName.setText(content.Name);
            imageViewProductImage.setImageBitmap(content.Image);
        }
    }
}