package com.hrenic.popularmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Provides network utilities
 */

public class NetworkUtility {

    private static final String TAG = "NetworkUtility";

    /**
     * Checks if there is network connectivity
     *
     * @param ctx context
     *
     * @return true if a connection exists
     */
    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * This method returns a contents retrieved from the HTTP request.
     *
     * @param url The URL to fetch the HTTP response from.
     *
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getContentFromURL(URL url) throws IOException {

        String result = null;

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                result = scanner.next();
            }

        } catch (IOException ex) {
            Log.v(TAG, "Error while getting contents from " + url, ex);
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }
}
