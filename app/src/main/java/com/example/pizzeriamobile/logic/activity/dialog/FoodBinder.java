package com.example.pizzeriamobile.logic.activity.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;
import com.example.pizzeriamobile.logic.userdata.Favourite;
import com.example.pizzeriamobile.logic.userdata.cart.Cart;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItem;
import com.example.pizzeriamobile.logic.userdata.cart.model.CartItemType;
import com.example.pizzeriamobile.model.http.receive.Product;

import java.util.ArrayList;

public class FoodBinder {

    final private ActivityDialog activity;
    private Product product;
    private Product.Variety variety;
    private String composition;
    private ArrayList<Integer> varietiesInCart;
    private ArrayList<String> spinnerItems;
    private int amount = 1;
    private boolean isFavourite = false;

    public FoodBinder(Context context){
        activity = (ActivityDialog) context;
        initialize();
    }

    private void initialize(){
        int id = activity.getIntent().getExtras().getInt(Appearance.Food.KEY,0);
        product = ControllerHandler.handler.getDataController().getById(id);
        bindListeners();
        loadData();
        selectVariety(0);
    }

    private void bindListeners(){
        activity.findViewById(R.id.imageButtonFavourite).setOnClickListener(onFavouriteClickListener);
        activity.findViewById(R.id.buttonDialogConfirm).setOnClickListener(onConfirmClickListener);
        activity.findViewById(R.id.buttonDialogCancel).setOnClickListener(onCancelClickListener);
        activity.findViewById(R.id.buttonDecrease).setOnClickListener(onDecreaseClickListener);
        activity.findViewById(R.id.buttonIncrease).setOnClickListener(onIncreaseClickListener);
        ((Spinner)activity.findViewById(R.id.spinnerDialogFoodVariety)).setOnItemSelectedListener(onVarietySelectedItemListener);
    }

    private void loadData(){
        variety = product.Varieties.get(0);
        varietiesInCart = findInCart(product.Id);
        isFavourite = findInFavourites(product.Id);
        spinnerItems = buildSpinnerList(varietiesInCart, product);
        composition = getComposition(product);

        displayGeneralData();
    }

    private ArrayList<Integer> findInCart(int productId){
        ArrayList<CartItem> items = Cart.handler.getVarietiesByProductId(productId);
        ArrayList<Integer> varieties = new ArrayList<>();
        for(CartItem item : items){
            varieties.add(item.Variety.Id);
        }
        return varieties;
    }

    private boolean findInFavourites(int productId){
        boolean result = Favourite.handler.contain(productId);
        return result;
    }

    private ArrayList<String> buildSpinnerList(ArrayList<Integer> varietiesInCart, Product product){
        ArrayList<String> items = new ArrayList<>();
        for(Product.Variety variety : product.Varieties){
            String item = (varietiesInCart.contains(variety.Id) ? "(Cart) " : "") + variety.Quantity + " " + variety.MeasurementUnit + " | " + variety.Price + " р.";
            items.add(item);
        }
        return items;
    }

    private String getComposition(Product product){
        String composition = activity.getResources().getString(R.string.inscriptionComposition);
        for(String item : product.Composition){
            composition+= "\n—\t" + item;
        }
        return composition;
    }

    private void displayGeneralData(){
        setTitle();
        setSpinnerList(spinnerItems);
        setFavourite(isFavourite);
        setComposition(composition);
    }

    private void setTitle(){
        String title = product.Name;
        activity.setTitle(title);
    }

    private void setSpinnerList(ArrayList<String> list){
        Spinner spinner = (Spinner) activity.findViewById(R.id.spinnerDialogFoodVariety);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item);
        adapter.addAll(list);
        spinner.setAdapter(adapter);
    }

    private void setFavourite(boolean isFavourite){
        if(isFavourite)
            ((ImageButton) activity.findViewById(R.id.imageButtonFavourite)).setImageResource(R.drawable.ic_dialog_favourites_on_24);
    }

    private void setComposition(String composition){
        TextView textView = (TextView) activity.findViewById(R.id.textViewDialogFoodComposition);
        textView.setText(composition);
    }

    private void selectVariety(int index){
        variety = product.Varieties.get(index);
        if(varietiesInCart.contains(variety.Id)){
            CartItem item = Cart.handler.getItemByVarietyId(variety.Id);
            amount = item.Amount;
        }
        else
            amount = 1;

        displayVarietyData();
        recalculate();
    }

    private void displayVarietyData(){
        TextView textView = (TextView) activity.findViewById(R.id.textViewDialogFoodPrice);
        EditText editText = (EditText) activity.findViewById(R.id.editTextDialogFoodAmount);

        textView.setText(variety.Price * amount + " р.");
        editText.setText(amount + "");
    }

    private void recalculate(){
        EditText editTextAmount = (EditText) activity.findViewById(R.id.editTextDialogFoodAmount);
        TextView textViewPrice = (TextView) activity.findViewById(R.id.textViewDialogFoodPrice);

        double price = amount * variety.Price;

        editTextAmount.setText(amount + "");
        textViewPrice.setText(price + " р.");
    }

    final private View.OnClickListener onFavouriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isFavourite) {
                boolean result = Favourite.handler.remove(product.Id);
                if(result) {
                    isFavourite = false;
                    ((ImageButton) view).setImageResource(R.drawable.ic_dialog_favourites_off_24);
                    Toast.makeText(activity, "Removed from favourite", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(activity, "Something goes wrong", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean result = Favourite.handler.put(product.Id);
                if(result) {
                    isFavourite = true;
                    ((ImageButton) view).setImageResource(R.drawable.ic_dialog_favourites_on_24);
                    Toast.makeText(activity, "Added to favourite", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(activity, "Something goes wrong", Toast.LENGTH_SHORT).show();
            }
        }
    };

    final private View.OnClickListener onConfirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(varietiesInCart.contains(variety.Id))
                Cart.handler.replace(product, variety, amount);
            else
                Cart.handler.add(product, variety, CartItemType.PRODUCT, amount);
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    };

    final private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.setResult(Activity.RESULT_CANCELED);
            activity.finish();
        }
    };

    final private View.OnClickListener onDecreaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(amount > 1) {
                amount--;
                recalculate();
            }
            else
                Toast.makeText(activity, "1 - is minimal value", Toast.LENGTH_SHORT).show();
        }
    };

    final private View.OnClickListener onIncreaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(amount < 10) {
                amount++;
                recalculate();
            }
            else
                Toast.makeText(activity, "10 - is maximum value", Toast.LENGTH_SHORT).show();
        }
    };

    final private AdapterView.OnItemSelectedListener onVarietySelectedItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int index, long id) {
            try {
                selectVariety(index);
            }
            catch (Exception ex){

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

}
