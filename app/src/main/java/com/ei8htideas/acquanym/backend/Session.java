package com.ei8htideas.acquanym.backend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ei8htideas.acquanym.MainActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Henry on 9/07/2018.
 */

public class Session {
    private static Details me;
    private static Set<Details> checkedUsers = new HashSet<>();
    private static MainActivity main;

    private static final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            me.latitude = location.getLatitude();
            me.longitude = location.getLongitude();
            Log.i("Session", "loc: " + me.latitude + ", " + me.longitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    public static void startLocationDetection() {
        LocationManager manager = (LocationManager)main.getSystemService(main.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);

        Log.i("Session", "Attempting location detection");
        Log.i("Session", providers.toString());

        for (String provider : providers) {
            try {
                Location location = manager.getLastKnownLocation(provider);
                if (location != null) {
                    me.longitude = location.getLongitude();
                    me.latitude = location.getLatitude();
                    Log.i("Session", "loc: " + me.latitude + ", " + me.longitude);
                } else {
                    Log.e("Session", "loc err");
                }
                manager.requestLocationUpdates(provider, 1000, 0, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMain(MainActivity main) {
        Session.main = main;
    }

    public static MainActivity getMain() {
        return main;
    }

    public static void setMyDetails(Details user) {
        me = user;
    }

    public static Details getMyDetails() {
        return me;
    }

    public static void addCheckedUser(Details user) {
        checkedUsers.add(user);
    }

    public static boolean userChecked(Details user) {
        return checkedUsers.contains(user);
    }

}
