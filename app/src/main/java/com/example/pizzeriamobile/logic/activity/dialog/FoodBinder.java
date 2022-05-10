package com.example.pizzeriamobile.logic.activity.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
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
    }

    private View.OnClickListener onFavouriteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(activity, "Added to favourite", Toast.LENGTH_SHORT).show();
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
