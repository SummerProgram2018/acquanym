package com.ei8htideas.acquanym.backend;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Henry on 9/07/2018.
 */

public class DBWriter {

    private final static String HOST = "https://acquanym.herokuapp.com/";

    private static void writeDB(String surl) {
        URL url = null;
        try {
            url = new URL(surl.replace(" ", "%20"));
            URLConnection request = url.openConnection();
            request.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLocation(Details me) {
        String surl = HOST + String.format("writelatlong?lat=%f&long=%f&id=%d",
                me.latitude, me.longitude, me.id);

        writeDB(surl);
    }


}
