package com.example.pizzeriamobile.logic.fragment.food.category;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.example.pizzeriamobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryListAdapter extends ExpandableRecyclerAdapter<Category, CategoryContent, CategoryListAdapter.ParentViewHolder, CategoryListAdapter.ChildViewHolder> {

    final LayoutInflater inflater;
    final ArrayList<Category> data;
    final Context context;

    public CategoryListAdapter(Context context, @NonNull ArrayList<Category> categories) {
        super(categories);
        inflater = LayoutInflater.from(context);
        data = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_category_parent_item, parentViewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.rgb(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255)));
            }
        });
        return new ParentViewHolder(view);
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_category_child_item, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull ParentViewHolder parentViewHolder, int parentPosition, @NonNull Category category) {
        parentViewHolder.bind(category);
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull CategoryContent content) {
        childViewHolder.bind(content);
    }

    public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ParentViewHolder<Category, CategoryContent>{
        final private TextView textViewCategoryName;

        public ParentViewHolder(@NonNull View view) {
            super(view);
            textViewCategoryName = view.findViewById(R.id.textViewCategoryName);
        }

        public void bind(Category category){
            textViewCategoryName.setText(category.Category);
        }
    }

    public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ChildViewHolder<List<CategoryContent>> {
        final RecyclerView recyclerViewCategoryContent;

        public ChildViewHolder(@NonNull View view) {
            super(view);
            recyclerViewCategoryContent = view.findViewById(R.id.recyclerViewCategoryContent);
        }

        public void bind(CategoryContent content){
            CategoryListContentAdapter adapter = new CategoryListContentAdapter(context, content.Content);
            recyclerViewCategoryContent.setAdapter(adapter);
        }
    }
}
