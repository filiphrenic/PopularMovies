package com.hrenic.popularmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
     * This method returns a JSON object retrieved from the HTTP request.
     *
     * @param url The URL to fetch the HTTP response from.
     *
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static JSONObject getJSONFromURL(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String result = scanner.next();
                try {
                    return new JSONObject(result);
                } catch (JSONException ex) {
                    Log.e(TAG, "JSON failed", ex);
                }
            }

            return null;

        } finally {
            urlConnection.disconnect();
        }

    }
}
