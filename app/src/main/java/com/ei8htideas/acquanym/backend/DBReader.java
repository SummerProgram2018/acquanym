package com.ei8htideas.acquanym.backend;

import android.os.AsyncTask;
import android.util.Log;

import com.ei8htideas.acquanym.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;

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

            Log.i("Reader", s);

            List<Map<Object, Object>> result = (List<Map<Object, Object>>)new JSONParser(s).parse();
            List<Details> detailList = new ArrayList<>();
            for(Map<Object, Object> map : result) {
                Details details = new Details();
                details.id = ((Double)map.get("id")).intValue();
                details.name = (String)map.get("name");
                details.latitude = (Double)map.get("latitude");
                details.longitude = (Double)map.get("longitude");
                details.distance = (Double)map.get("distance");
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


        Details tester = new Details(1, "me",  100, 100, 10, "student", "me", 19,  "Female", "a cool girl");
        Details tester2 = new Details(2, "me2",  100, 100, 110, "student", "me", 19,  "Female", "a cool girl");
        Details tester3 = new Details(3, "me3",  100, 100, 410, "student", "me", 19,  "Female", "a cool girl");
        Details tester4 = new Details(4, "me4",  100, 100, 510, "student", "me", 19,  "Female", "a cool girl");
        List<Details> test = new ArrayList<>();
        test.add(tester);
        test.add(tester2);
        test.add(tester3);
        test.add(tester4);



        return test; //threadExec(surl);
    }

    public static List<Details> searchAcqs(Details me, String order, String search) {
        String surl = HOST + String.format("searchacqs?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return threadExec(surl);
    }

    public static List<Details> searchAllUsers(Details me, String order) {
        String surl = HOST + String.format("searchallusers?lat=%f&long=%f&id=%d&order=%s",
                me.latitude, me.longitude, me.id, order);


        Details tester = new Details(1, "me",  100, 100, 10, "student", "me", 19,  "Female", "a cool girl");
        Details tester2 = new Details(2, "me2",  100, 100, 110, "student", "me", 19,  "Female", "a cool girl");
        Details tester3 = new Details(3, "me3",  100, 100, 410, "student", "me", 19,  "Female", "a cool girl");
        Details tester4 = new Details(4, "me4",  100, 100, 510, "student", "me", 19,  "Female", "a cool girl");
        List<Details> test = new ArrayList<>();
        test.add(tester);
        test.add(tester2);
        test.add(tester3);
        test.add(tester4);

        return test; //threadExec(surl);
    }

    public static List<Details> searchUsers(Details me, String order, String search) {
        String surl = HOST + String.format("searchusers?lat=%f&long=%f&id=%d&order=%s&search=%s",
                me.latitude, me.longitude, me.id, order, search);

        return threadExec(surl);
    }

    public static List<Details> getNearby(Details me, double range) {
        String surl = HOST + String.format("nearbyacqs?lat=%f&long=%f&range=%f&id=%d",
                me.latitude, me.longitude, range, me.id);

        return threadExec(surl);
    }

    public static List<Details> getAcqRequests(Details me) {
        String surl = HOST + String.format("confirmacq?lat=%f&long=%f&id=%d",
                me.latitude, me.longitude, me.id);

        return threadExec(surl);
    }

    private final static char[] HEX_ARR = "0123456789ABCDEF".toCharArray();

    private static String encodeHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARR[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARR[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static boolean verifyLogin(String username, String password) {
        String surl = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            String eUsername = encodeHexString(md5.digest(username.getBytes())).toLowerCase();
            String ePassword = encodeHexString(sha1.digest(password.getBytes())).toLowerCase();

            surl = HOST + String.format("verifylogin?username=%s&pword=%s",
                    eUsername, ePassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        List<Details> list = threadExec(surl);
        if(list.isEmpty()) {
            return false;
        }
        Session.setMyDetails(list.get(0));
        return true;
    }

    public static boolean newAccount(String username, String password, String name) {
        String surl = "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            String eUsername = encodeHexString(md5.digest(username.getBytes())).toLowerCase();
            String ePassword = encodeHexString(sha1.digest(password.getBytes())).toLowerCase();

            surl = HOST + String.format("newaccount?username=%s&pword=%s&name=%s",
                    eUsername, ePassword, name);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        List<Details> list = threadExec(surl);
        if(list.isEmpty()) {
            return false;
        }
        Session.setMyDetails(list.get(0));
        return true;
    }

    private static List<Details> threadExec(String surl) {
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

    private static class DBReaderThread extends Thread {
        public List<Details> result;
        public String surl;

        public void run() {
            result = readDB(surl);
        }
    }
}
