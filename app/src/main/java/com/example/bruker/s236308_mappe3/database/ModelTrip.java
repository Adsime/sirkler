package com.example.bruker.s236308_mappe3.database;

import android.graphics.PorterDuff;

/**
 * Created by bruker on 03.12.2016.
 */

public class ModelTrip {

    public int id, fromID, toID;
    public String from, to;

    public ModelTrip(int id, String from, String to, int fromID, int toID) {
        this.id = id;
        this.fromID = fromID;
        this.toID = toID;
        this.from = from;
        this.to = to;

    }

}
