package com.example.bruker.s236308_mappe3.utilities;

import org.json.JSONObject;

/**
 * Created by bruker on 14.11.2016.
 */

public class Stop {
    public static final String STOP_ID = "ID", STOP_NAME = "Name", DISTRICT = "District", PLACE_TYPE = "PlaceType", X = "X", Y = "Y";

    private int stopID;
    private String stopName, district, placeType, x, y;

    public Stop(int stopID) {
        this.stopID = stopID;
    }

    public Stop(JSONObject jsonObject) {
        try {
            stopID = jsonObject.getInt(STOP_ID);
            stopName = jsonObject.getString(STOP_NAME);
            district = jsonObject.getString(DISTRICT);
            placeType = jsonObject.getString(PLACE_TYPE);
            x = jsonObject.getString(X);
            y = jsonObject.getString(Y);
        } catch (Exception e) {

        }
    }

    public int getStopID() {
        return stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String toString() {
        return stopName + " (" + district + ")";
     }
}
