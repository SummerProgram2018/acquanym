package com.ei8htideas.acquanym.backend;

/**
 * Created by Henry on 5/07/2018.
 */

public class Details {
    public int id;
    public String name;
    public double latitude;
    public double longitude;
    public double distance;

    @Override
    public String toString() {
        return String.format("%d: %s (%f, %f, %f)", id, name, latitude, longitude, distance);
    }
}
