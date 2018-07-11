package com.ei8htideas.acquanym.background;


import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ei8htideas.acquanym.MainActivity;
import com.ei8htideas.acquanym.backend.DBReader;
import com.ei8htideas.acquanym.backend.DBWriter;
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Created by Henry on 6/07/2018.
 */

public class Subprocess extends Service {

    private MainActivity main;
    private Details me;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            setLastLoc();
            handler.postDelayed(runnable, SERVICE_DELAY);
        }
    };

    private FusedLocationProviderClient client;
    private Location lastLoc;
    private static final int SERVICE_DELAY = 1000*30*1;
    private static final int MIN_DIST = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        main = Session.getMain();
        me = Session.getMyDetails();
        handler.postDelayed(runnable, 0);
        return START_STICKY;
    }

    private void setLastLoc() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Log.e("Location", "No perms");
            return;
        }
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (lastLoc == null || (location != null && (location.getLatitude() != lastLoc.getLatitude()
                                || location.getLongitude() != lastLoc.getLongitude()))) {
                            me.latitude = location.getLatitude();
                            me.longitude = location.getLongitude();
                            lastLoc = location;
                            new DBWriter().writeLocation(me);
                            Log.d("Location", "Location written to DB: " + me.latitude + ", " + me.longitude);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Location", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private double calculateDistance(double lat1, double lat2, double long1, double long2) {
        double p = Math.PI / 180;
        double a = 0.5 - Math.cos((lat2 - lat1) * p)/2 + Math.cos(lat1 * p) * Math.cos(lat2 * p) *
                (1 - Math.cos((long2 - long1) * p)) / 2;
        return 12742 * Math.asin(Math.sqrt(a));
    }
}
