package com.ei8htideas.acquanym.backend;

/**
 * Created by Henry on 6/07/2018.
 */

public class Details {
    public int id;
    public String username;
    public String name;
    public String job;
    public double latitude;
    public double longitude;
    public double distance;
    public String title;

    public Details() {}

    public Details(int id, String name, double latitude, double longitude, double distance,
                   String title) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.title = title;
    }


    public String toString() {
        return id + ": " + username + name + job + " (" + latitude + ", " + longitude + ", " + distance + ")";
    }

    public boolean equals(Object o) {
        return o instanceof Details && ((Details) o).id == id && ((Details) o).name == name &&
                ((Details) o).job == job && ((Details) o).username == username &&
                ((Details) o).latitude == latitude && ((Details) o).longitude == longitude;
    }
}
