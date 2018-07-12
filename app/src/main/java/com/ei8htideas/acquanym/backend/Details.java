package com.ei8htideas.acquanym.backend;

/**
 * Created by Henry on 6/07/2018.
 */

public class Details {
    public int id;
    public String name;
    public String username;
    public int age;
    public String gender;
    public String title;
    public double latitude;
    public double longitude;
    public double distance;
    public String description;

    public Details() {}

    public Details(int id, String name, double latitude, double longitude, double distance,
                   String title, String username, int age, String gender, String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.title = title;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.description = description;
    }


    public String toString() {
        return id + ": " + username + name + title + " (" + latitude + ", " + longitude + ", " + distance + ")";
    }

    public boolean equals(Object o) {
        return o instanceof Details && ((Details) o).id == id && ((Details) o).name == name &&
                ((Details) o).title == title && ((Details) o).username == username &&
                ((Details) o).latitude == latitude && ((Details) o).longitude == longitude;
    }
}
