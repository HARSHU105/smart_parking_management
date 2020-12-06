package com.example.loginactivity.utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;



/**
 * Created by IN-RB on 04-06-2018.
 */

public class Utility {


    public static String PUSH_BROADCAST_ACTION = "Elite_Push_BroadCast_Action";
    public static String CHAT_BROADCAST_ACTION = "Elite_Chat_Action";
    public static String CHAT_DATA = "Elite_Chat_Data";
    public static String PUSH_NOTIFY = "notifyFlag";
    public static String PUSH_SHARED_DATA = "push_shared_data";
    public static String PUSH_DATA_FROM_LOGIN= "push_data_from_login";
    public static String PUSH_LOGIN_PAGE = "pushloginPage";




    public static File createDirIfNotExists() {

        File file = new File(Environment.getExternalStorageDirectory(), "/Elite-Cust");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");

            }
        }
        return file;
    }

}
