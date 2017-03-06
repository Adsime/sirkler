package com.example.bruker.s236308_mappe3.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bruker on 20.11.2016.
 */

public class QuickTrip {
    private String fromPlace, toPlace, travelTime, departure, arrival;
    private List<String> alternative;

    public QuickTrip(String from, String to, String travelTime, String departure, String arrival) {
        this.fromPlace = from;
        this.toPlace = to;
        this.travelTime = travelTime;
        this.departure = departure;
        this.arrival = arrival;
        this.alternative = new ArrayList<>();
    }

    public void addAlternativeItem(String item) {
        alternative.add(item);
    }

    @Override
    public String toString() {
        return "QuickTrip{" +
                "fromPlace='" + fromPlace + '\'' +
                ", toPlace='" + toPlace + '\'' +
                ", travelTime='" + travelTime + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", alternative=" + Arrays.toString(alternative.toArray()) +
                '}';
    }
}
