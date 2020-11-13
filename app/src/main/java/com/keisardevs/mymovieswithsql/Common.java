package com.keisardevs.mymovieswithsql;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Arrays;
import java.util.List;

public class Common {

    /**
     * The constant MOVIEDB_API_URL.
     */
    public final static String MOVIEDB_API_URL = "https://api.androidhive.info/json//";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private static final String LIST_SEPARATOR = "__,__";

    public static String convertListToString(List<String> stringList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : stringList) {
            stringBuilder.append(str).append(LIST_SEPARATOR);
        }

        // Remove last separator
        stringBuilder.setLength(stringBuilder.length() - LIST_SEPARATOR.length());

        return stringBuilder.toString();
    }

    public static List<String> convertStringToList(String str) {
        return Arrays.asList(str.split(LIST_SEPARATOR));
    }

}
