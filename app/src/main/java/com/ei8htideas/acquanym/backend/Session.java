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
