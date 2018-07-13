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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Henry on 9/07/2018.
 */

public class Session {
    private static Details me;
    private static MainActivity main;

    public static List<Details> getMyAcqs() {
        synchronized (lock) {
            return myAcqs;
        }
    }

    public static void setMyAcqs(List<Details> myAcqs) {
        Session.myAcqs = myAcqs;
    }

    public static List<Details> getUsers() {
        synchronized(lock) {
            return users;
        }
    }

    public static void setUsers(List<Details> users) {
        Session.users = users;
    }

    public static List<Details> getRequests() {
        synchronized(lock){
            return requests;
        }
    }

    public static void setRequests(List<Details> requests) {
        Session.requests = requests;
    }

    private static List<Details> myAcqs = new LinkedList<>();
    private static List<Details> users = new LinkedList<>();
    private static List<Details> requests = new LinkedList<>();

    public static boolean[] ready = {false, false, false};
    public static final Object lock = new Object();

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

    public static boolean isReady() {
        synchronized (lock) {
            return Session.ready[0] && Session.ready[1] && Session.ready[2];
        }
    }

}
