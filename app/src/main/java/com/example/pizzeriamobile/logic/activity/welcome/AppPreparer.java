package com.example.pizzeriamobile.logic.activity.welcome;

import android.content.Context;
import android.widget.Toast;

import com.example.pizzeriamobile.logic.App;
import com.example.pizzeriamobile.logic.controller.ControllerHandler;
import com.example.pizzeriamobile.logic.controller.DataController;
import com.example.pizzeriamobile.logic.handler.ServerConnectionHandler;
import com.example.pizzeriamobile.logic.preference.PreferencesHandler;
import com.example.pizzeriamobile.logic.userdata.Favourite;
import com.example.pizzeriamobile.logic.userdata.cart.Cart;
import com.google.android.material.bottomappbar.BottomAppBar;

/**Runs in Thread only one time per program run*/
public class AppPreparer implements Runnable{

    private static boolean isDone = false;

    @Override
    public void run() {
        Context context = App.context;
        if(isDone)
            return;
        initialize(context);
        isDone = true;
    }

    private void initialize(Context context){
        ServerConnectionHandler.create(context);
        ControllerHandler.create(context);

        DataController controller = ControllerHandler.getHandler().getDataController();
        while(!(controller.isDataLoadProcessComplete() || controller.dataLoadResult())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.yield();
            }
        }

        PreferencesHandler.create(context);
        Favourite.load();
        Cart.restore();
    }

    public boolean isDome(){
        return isDone;
    }
}
