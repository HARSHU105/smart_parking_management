package com.example.loginactivity.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by IN-RB on 02-02-2018.
 */

public class Constants {

    public static int SPLASH_DISPLAY_LENGTH = 2000;



    public static void hideKeyBoard(View view, Context context) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean checkInternetStatus(Context context) {
      /*  ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                conMgr.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                conMgr.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        }*/
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            return true;
        }
        return false;
    }
}
