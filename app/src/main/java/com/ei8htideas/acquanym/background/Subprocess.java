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
import com.ei8htideas.acquanym.backend.Details;
import com.ei8htideas.acquanym.backend.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by Henry on 6/07/2018.
 */

public class Subprocess extends Service implements LocationListener {

    private MainActivity context = Session.getMain();
    private boolean isGpsEnabled = false;
    private boolean isNetEnabled = false;
    private boolean canGetLocation = false;
    private Location location;
    private Details me = Session.getMyDetails();

    private static final float REFRESH_DIST = 5; // 50 meters
    private static final long REFRESH_TIME = 1000 * 10 * 1; // 1 minute
    protected LocationManager locationManager;

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final int LOCATION_REQUEST = 1;

    public Subprocess() {
        //getLocation();
    }

    /*public Location getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetEnabled) {
                Log.e("Subprocess", "No location services available");
            } else {
                this.canGetLocation = true;
                if(isGpsEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            Log.d("GPS Enabled", "Getting location");
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                me.latitude = location.getLatitude();
                                me.longitude = location.getLongitude();
                                Log.i("Location", "GPS: " + me.latitude + ", " + me.longitude);
                            }
                        }
                    }
                } else if (isNetEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            me.latitude = location.getLatitude();
                            me.longitude = location.getLongitude();
                            Log.i("Location", "Net: " + me.latitude + ", " + me.longitude);
                        }
                    }
                }
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

        return location;
    }*/

    @Override
    public void onCreate() {
        getLastLoc();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.i("Location", loc.toString());
        } catch (SecurityException ex) {
            try {
                Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.i("NetLocation", loc.toString());
            } catch (SecurityException ex2) {
                Log.e("Location", "Unable to fetch");
            }
        }
        Log.i("Location", "setup");*/
    }

    private void getLastLoc() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                getLastLoc();
            }
            return;
        }
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            Log.i("Location", location.getLatitude() + ", " + location.getLongitude());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Location", location.getLatitude() + ", " + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
// TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
// TODO Auto-generated method stub

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
// TODO Auto-generated method stub
    }
    @Override
    public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
        return null;
    }
}
