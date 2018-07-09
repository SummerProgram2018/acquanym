package com.ei8htideas.acquanym.backend;

/**
 * Created by Henry on 6/07/2018.
 */

public class Details {
    public int id;
    public String name;
    public double latitude;
    public double longitude;
    public double distance;

    public String toString() {
        return id + ": " + name + " (" + latitude + ", " + longitude + ", " + distance + ")";
    }

    public boolean equals(Object o) {
        return o instanceof Details && ((Details)o).id == id && ((Details)o).name == name &&
                ((Details)o).latitude == latitude && ((Details)o).longitude == longitude;
    }
}
