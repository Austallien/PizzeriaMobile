package com.example.pizzeriamobile.core.activity.welcome;

import android.content.Context;

import com.example.pizzeriamobile.core.App;
import com.example.pizzeriamobile.core.controller.ControllerHandler;
import com.example.pizzeriamobile.core.controller.DataController;

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
        DataController controller = ControllerHandler.handler.getDataController();
        while(!(controller.isDataLoadProcessComplete() || controller.dataLoadResult())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.yield();
            }
        }
    }

    public boolean isDome(){
        return isDone;
    }
}
