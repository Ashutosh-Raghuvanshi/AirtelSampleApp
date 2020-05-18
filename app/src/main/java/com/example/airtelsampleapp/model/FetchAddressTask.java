package com.example.airtelsampleapp.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchAddressTask extends AsyncTask<String, String, String> {
    private final String TAG = FetchAddressTask.class.getSimpleName();

    @Override
    protected String doInBackground(String... strings) {
        // If there's no zip code, there's nothing to look up.  Verify size of params.
        if (strings.length == 0) {
            return null;
        }
        String city = strings[1];
        String address = strings[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String addressJsnStr = null;

        try {
            final String ADDRESS_BASE_URL =
                    "https://digi-api.airtel.in/compassLocation/rest/address/autocomplete?";
            final String QUERY_PARAM = "queryString";
            final String CITY_PARAM = "city";

            Uri builtUri = Uri.parse(ADDRESS_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, address)
                    .appendQueryParameter(CITY_PARAM, city)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            addressJsnStr = buffer.toString();

            return addressJsnStr;
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
