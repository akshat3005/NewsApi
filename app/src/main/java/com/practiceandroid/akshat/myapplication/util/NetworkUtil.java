package com.practiceandroid.akshat.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by akshat-3049 on 28/05/18.
 */

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if( activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }

        Toast.makeText(context,"Looks like you are not connected to the internet...please connect to a network and try again",Toast.LENGTH_LONG).show();
        return false;
    }

}
