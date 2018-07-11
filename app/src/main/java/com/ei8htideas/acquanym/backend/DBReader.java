package com.ei8htideas.acquanym.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private List<Details> readDB(String surl) {
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

    public List<Details> searchAllAcqs(Details me, String order) {
        String surl = HOST + String.format("searchallacqs?lat=%f&long=%f&id=%d&order=%s",
                me.latitude, me.longitude, me.id, order);

        return threadExec(surl);
    }

    public List<Details> searchAcqs(Details me, String order, String search) {
        String surl = HOST + String.format("searchacqs?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return threadExec(surl);
    }

    public List<Details> searchAllUsers(Details me, String order) {
        String surl = HOST + String.format("searchallusers?lat=%f&long=%f&id=%d&order=%s",
                me.latitude, me.longitude, me.id, order);

        return threadExec(surl);
    }

    public List<Details> searchUsers(Details me, String order, String search) {
        String surl = HOST + String.format("searchusers?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return threadExec(surl);
    }

    public List<Details> getNearby(Details me, double range) {
        String surl = HOST + String.format("nearbyacqs?lat=%f&long=%f&range=%f&id=%d",
                me.latitude, me.longitude, range, me.id);

        return threadExec(surl);
    }

    public List<Details> getAcqRequests(Details me) {
        String surl = HOST + String.format("confirmacq?lat=%f&long=%f&id=%d",
                me.latitude, me.longitude, me.id);

        return threadExec(surl);
    }

    private List<Details> threadExec(String surl) {
        DBReaderThread t = new DBReaderThread();
        t.surl = surl;
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t.result;
    }

    private class DBReaderThread extends Thread {
        public List<Details> result;
        public String surl;

        public void run() {
            result = readDB(surl);
        }
    }
}
