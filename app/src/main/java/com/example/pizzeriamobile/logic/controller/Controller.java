package com.example.pizzeriamobile.logic.controller;

import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

import java.util.concurrent.Callable;

public class Controller implements Runnable{

    final private Thread thread;

    protected boolean isProcessComplete;
    protected boolean processResult;
    protected boolean isTaskExecuting;

    private Runnable task;

    /**Initialize new controller
     * @param ThreadName set name of thread;
     * */
    protected Controller(String ThreadName){
        thread = new Thread(this, ThreadName);
    }

    /**Starts task and returns thread. If this one is already running, then returns thread only
     * @return {@link Thread}
     * */
    public Thread execute(Runnable runnable){
        if(!isTaskExecuting) {
            task = runnable;
            isTaskExecuting = true;
        }
        return thread;
    }

    /**
     * */
    @Override
    public void run() {
        while(true) {
            Thread.yield();
            if (!isTaskExecuting)
                continue;

            isProcessComplete = false;
            task.run();
            task = null;
            isProcessComplete = true;
            isTaskExecuting = false;
        }
    }
}
