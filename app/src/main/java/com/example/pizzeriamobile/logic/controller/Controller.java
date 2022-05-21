package com.example.pizzeriamobile.logic.controller;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.arch.core.executor.TaskExecutor;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

abstract public class Controller<T> implements Runnable{

    final private Thread thread;

    protected String url;

    protected boolean isProcessComplete;
    protected boolean isTaskExecuting;
    protected boolean processResult;

    private Runnable task;

    protected T data;

    /**Initialize new controller
     * @param ThreadName set name of thread;
     * */
    protected Controller(String ThreadName){
        thread = new Thread(this, ThreadName);
        thread.start();
    }

    /**Starts task and returns thread. If this one is already running, then returns thread only
     * @return {@link Thread}
     * */
    protected Thread execute(Runnable runnable){
        if(!isTaskExecuting) {
            task = runnable;
            isTaskExecuting = true;
        }
        return thread;
    }

    abstract protected void reload(Object... args);

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

    abstract protected JSONObject load();

    abstract protected T convert(JSONObject object);

    public boolean isProcessComplete(){
        return isProcessComplete;
    }

    public boolean isTaskExecuting(){
        return isTaskExecuting;
    }

    public boolean processResult(){
        return processResult;
    }
}
