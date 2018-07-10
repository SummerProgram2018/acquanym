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

            System.out.println(s);
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

    public static List<Details> searchAllAcqs(Details me, String order) {
        String surl = HOST + String.format("searchallacqs?lat=%f&long=%f&id=%d&order=%s",
                me.latitude, me.longitude, me.id, order);

        return readDB(surl);
    }

    public static List<Details> searchAcqs(Details me, String order, String search) {
        String surl = HOST + String.format("searchacqs?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return readDB(surl);
    }

    public static List<Details> searchAllUsers(Details me, String order) {
        String surl = HOST + String.format("searchallusers?lat=%f&long=%f&id=%d&order=%s",
                me.latitude, me.longitude, me.id, order);

        ArrayList<Details> help = new ArrayList<Details>();

        Details yes = new Details();
        yes.id = 1;
        yes.username = "yo";
        yes.name="yes";
        yes.job = "student";
        yes.latitude=100.10;
        yes.longitude=100;
        yes.distance=20;
        help.add(yes);

        Details no = new Details();
        no.id = 2;
        no.username = "nsdo";
        no.name="no";
        no.job = "studentz";
        no.latitude=109;
        no.longitude=-27;
        no.distance=150;
        help.add(no);

        return help; //readDB(surl);
    }

    public static List<Details> searchUsers(Details me, String order, String search) {
        String surl = HOST + String.format("searchusers?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return readDB(surl);
    }

    public static List<Details> getNearby(Details me, double range) {
        String surl = HOST + String.format("nearby?lat=%f&long=%f&range=%f",
                me.latitude, me.longitude, range);

        return readDB(surl);
    }
}
