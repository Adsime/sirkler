package com.example.bruker.s236308_mappe3.models;

/**
 * Created by bruker on 03.12.2016.
 */

public class RedusedTravelProposal {
    public int id, toID, fromID;
    public String from, to, departure, arrival, totalTime;

    public RedusedTravelProposal(int id, String from, String to, String departure, String arrival, String totalTime, int toID, int fromID) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.departure = departure;
        this.arrival = arrival;
        this.totalTime = totalTime;
        this.toID = toID;
        this.fromID = fromID;
    }
}
