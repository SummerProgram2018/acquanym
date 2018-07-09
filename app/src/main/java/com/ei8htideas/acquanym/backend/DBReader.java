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
            URL url = new URL(surl.replace(" ", "%20"));
            URLConnection request = url.openConnection();
            request.connect();
            InputStreamReader reader = new InputStreamReader((InputStream) request.getContent());

            int i;
            String s = "";
            while((i = reader.read()) != -1) {
                s += String.valueOf((char)i);
            }

            List<Map<Object, Object>> result = (List<Map<Object, Object>>)new JSONParser(s).parse();
            List<Details> detailList = new ArrayList<>();
            for(Map<Object, Object> map : result) {
                Details details = new Details();
                details.id = ((Double)map.get("id")).intValue();
                details.name = (String)map.get("name");
                details.latitude = (double)map.get("latitude");
                details.longitude = (double)map.get("longitude");
                details.distance = (double)map.get("distance");
                detailList.add(details);
            }
            return detailList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Details> searchAllAcqs(double my_lat, double my_long, int my_id,
                                              String order) {
        String surl = HOST + String.format("searchallacqs?lat=%f&long=%f&id=%d&order=%s",
                my_lat, my_long, my_id, order);

        return readDB(surl);
    }

    public static List<Details> searchAcqs(double my_lat, double my_long, int my_id,
                                           String order, String search) {
        String surl = HOST + String.format("searchacqs?lat=%f&long=%f&id=%d&order=%s&search=%s",
                my_lat, my_long, my_id, order, search);

        return readDB(surl);
    }

    public static List<Details> searchAllUsers(double my_lat, double my_long, int my_id,
                                               String order) {
        String surl = HOST + String.format("searchallusers?lat=%f&long=%f&id=%d&order=%s",
                my_lat, my_long, my_id, order);

        return readDB(surl);
    }

    public static List<Details> searchUsers(double my_lat, double my_long, int my_id,
                                            String order, String search) {
        String surl = HOST + String.format("searchusers?lat=%f&long=%f&id=%d&order=%s&search=%s",
                my_lat, my_long, my_id, order, search);

        return readDB(surl);
    }

    public static List<Details> getNearby(double my_lat, double my_long, double range) {
        String surl = HOST + String.format("nearby?lat=%f&long=%f&range=%f",
                my_lat, my_long, range);

        return readDB(surl);
    }
}