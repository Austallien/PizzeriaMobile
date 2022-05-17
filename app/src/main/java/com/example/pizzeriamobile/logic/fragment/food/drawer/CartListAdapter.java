package com.example.pizzeriamobile.logic.fragment.food.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Visibility;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.logic.model.http.Product;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    LayoutInflater inflater;
    ArrayList<CartItem> content;
    int buttonRemoveVisibility;

    public CartListAdapter(Context context, ArrayList<CartItem> cart, int buttonRemoveVisibility){
        inflater = LayoutInflater.from(context);
        content = cart;
        this.buttonRemoveVisibility = buttonRemoveVisibility;
    }

    public void setCart(ArrayList<CartItem> cart){
        content = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_food_drawer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position, content.get(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        Button buttonRemove;
        TextView textViewNumber;
        TextView textViewName;
        TextView textViewVariety;
        TextView textViewAmount;
        TextView textViewPrice;
        TextView textViewTotal;

        public ViewHolder(View view){
            super(view);
            buttonRemove = (Button) view.findViewById(R.id.buttonFoodDrawerRemove);
            textViewNumber = (TextView) view.findViewById(R.id.textViewFoodDrawerNumberValue);
            textViewName = (TextView) view.findViewById(R.id.textViewFoodDrawerNameValue);
            textViewVariety = (TextView) view.findViewById(R.id.textViewFoodDrawerVarietyValue);
            textViewAmount = (TextView) view.findViewById(R.id.textViewFoodDrawerAmountValue);
            textViewPrice = (TextView) view.findViewById(R.id.textViewFoodDrawerPriceValue);
            textViewTotal = (TextView) view.findViewById(R.id.textViewFoodDrawerTotalValue);
        }

        public void bind(int position, CartItem item) {
            Product product = item.Product;
            Product.Variety variety = item.Variety;

            String textNumber = String.valueOf(position).length() == 1 ? "0" + position : String.valueOf(position);
            String textName = product.Name;
            String textVariety = variety.Quantity + " " + variety.MeasurementUnit;
            String textAmount = item.Amount + "";
            String textPrice = variety.Price + " р.";
            String textTotal = item.Amount * variety.Price + " р.";

            buttonRemove.setVisibility(buttonRemoveVisibility);
            textViewNumber.setText(textNumber);
            textViewName.setText(textName);
            textViewVariety.setText(textVariety);
            textViewAmount.setText(textAmount);
            textViewPrice.setText(textPrice);
            textViewTotal.setText(textTotal);
        }
    }
}