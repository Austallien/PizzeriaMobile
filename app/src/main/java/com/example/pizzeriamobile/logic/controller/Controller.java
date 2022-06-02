package com.example.pizzeriamobile.logic.controller;

abstract public class Controller<ReceivingType> implements Runnable{

    final private Thread thread;

    protected String url;

    protected boolean isProcessComplete;
    protected boolean isTaskExecuting;
    protected boolean processResult;

    private Runnable task;

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

    abstract protected ReceivingType load();

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
