package controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URISyntaxException;


public class AccountChecker {
    private static class Account{
        private static String UserName = "";
        private static String passwd = "";
    }


    public static String[] readObject(){

        try {
            File f = new File(AccountChecker.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            String str = f.getPath();
            String tex = "/";
            String [] tok = str.split("/");
            for (int i = 0; i < tok.length-1;i++) {
                tex = tex + "/" +tok[i];
            }

            FileReader fis = new FileReader(tex +"/classes/data.json");

            JSONParser jsp = new JSONParser();

            Object obj =jsp.parse(fis);
            JSONArray jarr = (JSONArray)obj;

            jarr.forEach(e -> {
                Account.UserName = (String) ((JSONObject)e).get("UserName");
                Account.passwd = (String) ((JSONObject)e).get("passwd");


            });


        } catch (URISyntaxException | IOException | ParseException e) {
            e.printStackTrace();
        }

        return new String[]{Account.UserName, Account.passwd};

    }


    public static void main(String[] args) {
        AccountChecker.readObject();
    }


    public static String getName() {
        return Account.UserName;
    }
}
