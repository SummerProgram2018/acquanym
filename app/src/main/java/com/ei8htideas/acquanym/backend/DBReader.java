package com.ei8htideas.acquanym.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Henry on 6/07/2018.
 */

public class DBReader {

    private final static String HOST = "https://acquanym.herokuapp.com/";

    private static List<Details> readDB(String surl) {
        try {
            URL url = new URL(surl);
            URLConnection request = url.openConnection();
            request.connect();
            InputStreamReader reader = new InputStreamReader((InputStream) request.getContent());

            JSONParser parser = new JSONParser();
            parser.start();
            int i;
            while((i = reader.read()) != -1) {
                parser.feed((char)i);
            }
            List<Map<Object, Object>> result = (List<Map<Object, Object>>)parser.getResult();
            List<Details> detailList = new ArrayList<>();
            for(Map<Object, Object> map : result) {
                Details details = new Details();
                details.id = ((Double)map.get("id")).intValue();
                details.name = (String)map.get("name");
                details.latitude = (double)map.get("lat");
                details.longitude = (double)map.get("long");
                details.distance = (double)map.get("dist");
                detailList.add(details);
            }
            return detailList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Details> getNearby(double my_lat, double my_long, double range) {
        String surl = HOST + String.format("nearby?lat=%f&long=%f&range=%f",
                my_lat, my_long, range);

        return readDB(surl);
    }
}
