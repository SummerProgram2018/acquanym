package com.ei8htideas.acquanym.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henry on 5/07/2018.
 */

public class BackendConnection {
    private final static String HOST = "https://acquanym.herokuapp.com/";

    private static List<Details> read(String surl) {
        /*try {
            URL url = new URL(surl);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            Gson gson = new Gson();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonArray arr = root.getAsJsonArray();
            List<Details> res = new ArrayList<>();
            for(JsonElement elem : arr) {
                res.add(gson.fromJson(elem, Details.class));
            }
            return res;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static List<Details> getNearby(double latitude, double longitude, double range) {
        String surl = HOST + String.format("nearby?lat=%flong=%frange=%f",
                latitude, longitude, range);

        return read(surl);
    }

    public static void main(String[] args) {
        System.out.println(getNearby(100, 100, 0.01));
    }
}
