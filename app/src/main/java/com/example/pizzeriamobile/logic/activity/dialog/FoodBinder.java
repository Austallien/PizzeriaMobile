package com.example.pizzeriamobile.logic.activity.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityDialog;

public class FoodBinder {

    final private ActivityDialog activity;

    public FoodBinder(Context context){
        activity = (ActivityDialog) context;
        bind();
    }

    private void bind(){
        activity.findViewById(R.id.imageButtonFavourite).setOnClickListener(onFavouriteClickListener);
        activity.findViewById(R.id.buttonDialogConfirm).setOnClickListener(onConfirmClickListener);
        activity.findViewById(R.id.buttonDialogCancel).setOnClickListener(onCancelClickListener);

        ImageButton asd = (ImageButton)activity.findViewById(R.id.imageButtonFavourite);
    }

    private View.OnClickListener onFavouriteClickListener = new View.OnClickListener() {
        private boolean isFavourite = false;

        @Override
        public void onClick(View view) {
            if(isFavourite) {
                isFavourite = false;
                Toast.makeText(activity, "Removed from favourite", Toast.LENGTH_SHORT).show();
                ((ImageButton)view).setImageResource(R.drawable.ic_dialog_favourites_off_24);
            }
            else{
                isFavourite = true;
                Toast.makeText(activity, "Added to favourite", Toast.LENGTH_SHORT).show();
                ((ImageButton)view).setImageResource(R.drawable.ic_dialog_favourites_on_24);
            }
        }
    };

    private View.OnClickListener onConfirmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            activity.setResult(Activity.RESULT_CANCELED);
            activity.finish();
        }
    };

}
