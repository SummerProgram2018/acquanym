package com.ei8htideas.acquanym.background;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Henry on 6/07/2018.
 */

public class Subprocess extends Service {

    private final static int INTERVAL = 1000*60*2;
    private Handler handler = new Handler();
    private Runnable handlerTask = new Runnable() {
        public void run() {
            Log.i("Subprocess", "yay");
            handler.postDelayed(handlerTask, INTERVAL);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handlerTask.run();
        return Service.START_STICKY;
    }
}
