package com.example.bruker.s236308_mappe3.interfaces;

import org.json.JSONArray;

/**
 * Created by bruker on 15.11.2016.
 */

public interface Listener {
    int STOP_BY_ID_RESPONSE = 10001;
    int QUICK_TRIP = 10002;
    int QUICK_TRIP_SEARCH = 10003;
    int QUICK_TRIP_SEARCH_RESPONSE = 10005;
    int QUICK_TRIP_SAVE = 10004;
    int BACK_BUTTON = 10006;
    int OPEN_QUICKTRIP = 10007;
    int OPEN_SAVED_TRIPS = 10008;
    int OPEN_LOCATION = 10009;
    int DELETE_QUICK_TRIP = 10010;
    int LIST_RESPONSE = 10011;
    int LIST_DELETE_MODE = 10012;
    int LIST_DELETE = 10013;
    int REFRESH_LIST = 10014;
    void onClick(Object o, int interaction);
    void onPost(int tripID, int responseType, JSONArray arr);
    void textInput(String input);
}
