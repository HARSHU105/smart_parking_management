package com.example.loginactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    String TAG = "ERROR_TAG";
    String TAG_REFERRER = "REFERRER";
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "SMART_PARKING_CUSTOMER";

    private static final String USER_NAME = "SMART_PARKING_USER_NAME";
    private static final String MOBILE = "SMART_PARKING_MOBILE";
    private static final String EMAILID = "SMART_PARKING_EMAIL_ID";
    private static final String PASSWORD = "SMART_PARKING_PASSWORD";


    private static final String IS_DEVICE_TOKEN = "devicetoken";
    public static String DEVICE_ID = "deviceID";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserName(String struser) {
        editor.putString(USER_NAME, struser);

        editor.commit();
    }

    public String geUserName() {
        return pref.getString(USER_NAME, "");
    }

    public void setMobile(String mob) {
        editor.putString(MOBILE, mob);

        editor.commit();
    }

    public String geMobile() {
        return pref.getString(MOBILE, "");
    }

    public void setEmailID(String strEmail) {
        editor.putString(EMAILID, strEmail);

        editor.commit();
    }

    public String geEmailID() {
        return pref.getString(EMAILID, "");
    }


    public void setDeviceID(String deviceID) {
        editor.putString(DEVICE_ID, deviceID);

        editor.commit();
    }

    public String getDeviceID() {
        return pref.getString(DEVICE_ID, "");
    }

    public void clearUserCache() {

        editor.remove(PASSWORD);
        editor.remove(MOBILE);
        editor.remove(USER_NAME);
        editor.remove(PASSWORD);
        editor.clear();
        editor.commit();
    }
}