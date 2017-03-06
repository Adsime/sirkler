package com.example.bruker.s236308_mappe3.models;

import com.example.bruker.s236308_mappe3.utilities.Stop;

import java.util.List;

/**
 * Created by bruker on 30.11.2016.
 */

public class Trip {
    public int id;
    private List<TravelProposal> proposals;
    private String totalTime, departure, arrival;
    int fromID, toID;

    public Trip(List<TravelProposal> proposals, String totalTime, String departure, String arrival, int fromID, int toID) {
        this.proposals = proposals;
        this.totalTime = convertTime(totalTime.split(":"));
        this.departure = departure.split("T")[1];
        this.arrival = arrival.split("T")[1];
        this.fromID = fromID;
        this.toID = toID;
    }

    private String convertTime(String[] times) {
        return ((Integer.parseInt(times[0]) == 0)?"":Integer.parseInt(times[0])+"t") +
                ((Integer.parseInt(times[1]) == 0)?"":Integer.parseInt(times[1])+"m") +
                ((Integer.parseInt(times[2]) == 0)?"":Integer.parseInt(times[2])+"s");
    }

    public List<TravelProposal> getProposals() {
        return proposals;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public int getFromID() {
        return fromID;
    }

    public int getToID() {
        return toID;
    }

    @Override
    public String toString() {
        return totalTime + " " + departure + " " + arrival;
    }
}
