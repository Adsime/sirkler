package com.example.bruker.s236308_mappe3.utilities;

import android.os.AsyncTask;

import com.example.bruker.s236308_mappe3.interfaces.Listener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bruker on 14.11.2016.
 */

public class JsonRequester {
    public static final String STOP_BY_ID = "http://reisapi.ruter.no/Place/GetPlaces?id=";
    public static final String TRIP_INFO_FROM_TO = "http://reisapi.ruter.no/Travel/GetTravels?";

    public static final int CLOSE_RESPONSE = 100001;
    public static final int CUSTOM_RESPONSE = 100002;

    public static final String TRAVELPROPOSALS = "TravelProposals";

    public static final String IS_AFTER = "&isAfter=true";
    public static final String TO_PLACE = "toPlace=";
    public static final String FROM_PLACE = "fromPlace=";
    private Listener listener;

    public JsonRequester(Listener listener) {
        this.listener = listener;
    }

    public void getTravels(final Stop from, final Stop to, final int proposals, final int responseCode, final int tripID) {
        new AsyncTask<JSONArray, String, JSONArray>() {
            @Override
            protected JSONArray doInBackground(JSONArray... jsonArrays) {
                JSONArray output;
                String next;
                String query = TRIP_INFO_FROM_TO+FROM_PLACE+from.getStopID()+"&"+TO_PLACE+to.getStopID()+"&"+IS_AFTER+"&"+proposals;
                try {
                    URI uri = new URI(query);
                    URL url = new URL(uri.toASCIIString());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer buffer = new StringBuffer();

                    while ((next = br.readLine()) != null) {
                        buffer.append(next);
                    }
                    JSONObject obj = new JSONObject(buffer.toString());
                    output = obj.getJSONArray("TravelProposals");
                    connection.disconnect();
                } catch (Exception e) {
                    return null;
                }
                return output;
            }

            @Override
            protected void onPostExecute(JSONArray array) {
                listener.onPost(tripID, responseCode, array);
            }
        }.execute();
    }

    public void getStop(final String id, final int responseID) {
        new AsyncTask<JSONArray, String, JSONArray>() {
            @Override
            protected JSONArray doInBackground(JSONArray... strings) {
                JSONArray output;
                String next;
                String query = STOP_BY_ID + id.replaceAll("\\s", "+");
                try {
                    URI uri = new URI(query);
                    URL url = new URL(uri.toASCIIString());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer buffer = new StringBuffer();

                    while ((next = br.readLine()) != null) {
                        buffer.append(next);
                    }
                    output = new JSONArray(buffer.toString());
                    connection.disconnect();
                } catch (Exception e) {
                    return null;
                }
                return output;
            }

            @Override
            protected void onPostExecute(JSONArray s) {
                listener.onPost(-1,responseID, s);
            }
        }.execute();
    }

    public void getTripInfo(final int fromPLace, final int toPlace) {
        new AsyncTask<JSONArray, String, JSONArray>() {

            @Override
            protected JSONArray doInBackground(JSONArray... jsonArrays) {
                JSONArray output;
                String next;
                String query = new StringBuilder().append(TRIP_INFO_FROM_TO).append(FROM_PLACE)
                        .append(fromPLace).append("&").append(TO_PLACE).append(toPlace).append("&isAfter=true").toString();
                try {
                    URL url = new URL(query);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Accept", "application/json");
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer buffer = new StringBuffer();
                    while ((next = br.readLine()) != null) {
                        buffer.append(next);
                    }
                    JSONObject jsonObject = new JSONObject(buffer.toString());
                    output = jsonObject.getJSONArray(TRAVELPROPOSALS);
                    connection.disconnect();
                } catch (Exception e) {
                    return null;
                }
                return output;
            }

            @Override
            protected void onPostExecute(JSONArray jsonArray) {
                listener.onPost(-1, Listener.QUICK_TRIP, jsonArray);
            }
        }.execute();
    }

}
