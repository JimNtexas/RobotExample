package com.grayraven.robotexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class MyService extends Service {
    private static final String TAG ="MyService";
    private boolean isRunning  = false;
    public MyService() {
    }

    @Override
    public void onCreate() {
        isRunning  = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");
        isRunning = true;
        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                int cnt = 0;
                if(isRunning){
                    Log.i(TAG, "Service running");
                } 

                //NOTE: See this SO post about the traditional ways to send a message from a service to an activity:
                //http://stackoverflow.com/questions/12997463/send-intent-from-service-to-activity
                //I think using an event bus is less trouble.

                while(isRunning) {
                    try {
                        Thread.sleep(5000);
                        //send toast message to main activity
                        String msg = "Service count: " + cnt;
                        Log.d(TAG, "message posted: " + cnt);
                        EventBus.getDefault().post(new MessageEvent(msg));
                        cnt++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "timer loop exiting");
            }
        }).start();

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "Service onDestroy");
        super.onDestroy();
    }
}
