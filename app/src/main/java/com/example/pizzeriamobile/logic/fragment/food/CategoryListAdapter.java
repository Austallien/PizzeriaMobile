package com.example.pizzeriamobile.logic.fragment.food;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeriamobile.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    final LayoutInflater inflater;
    final List<State> states;

    public CategoryListAdapter(Context context, List<State> states){
        inflater = LayoutInflater.from(context);
        this.states = states;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_category_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        State state = states.get(position);
        holder.textViewCategoryName.setText(state.getCategoryName());

        ArrayList<CategoryListContentAdapter.State> contentStateList = new ArrayList<CategoryListContentAdapter.State>();
        for(int i = 0; i < state.contentStates.size(); i++){
            CategoryListContentAdapter.State _state = state.contentStates.get(i);
            contentStateList.add(new CategoryListContentAdapter.State(_state.productImage, _state.productName));
        }

        CategoryListContentAdapter adapter = new CategoryListContentAdapter(holder.itemView.getContext(), contentStateList);
        holder.recyclerViewCategoryContent.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView textViewCategoryName;
        final RecyclerView recyclerViewCategoryContent;
        public ViewHolder(@NonNull View view) {
            super(view);
            textViewCategoryName = (TextView) view.findViewById(R.id.textViewCategoryName);
            recyclerViewCategoryContent = (RecyclerView) view.findViewById(R.id.recycleViewCategoryContent);
        }
    }

    public static class State{
        private String categoryName;
        private ArrayList<CategoryListContentAdapter.State> contentStates;

        public State(String categoryName, ArrayList<CategoryListContentAdapter.State> contentStates){
            this.categoryName = categoryName;
            this.contentStates = contentStates;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public ArrayList<CategoryListContentAdapter.State> getContentStates() {
            return contentStates;
        }
    }
}
