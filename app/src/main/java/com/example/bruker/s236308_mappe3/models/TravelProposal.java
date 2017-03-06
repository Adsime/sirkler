package com.example.bruker.s236308_mappe3.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bruker on 30.11.2016.
 */

public class TravelProposal {

    private String line, type, totaltime, departureTime, timeToDeparture, arrivalPoint, departurePoint;


    public TravelProposal(String line, String type, String totaltime, String departureTime, String arrivalPoint, String departurePoint) {
        this.departureTime = departureTime.split("T")[1];
        Calendar c = Calendar.getInstance();
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String[] times = totaltime.split(":");
        this.totaltime = ((Integer.parseInt(times[0]) == 0)?"":Integer.parseInt(times[0])+"t") +
                ((Integer.parseInt(times[1]) == 0)?"":Integer.parseInt(times[1])+"m") +
                ((Integer.parseInt(times[2]) == 0)?"":Integer.parseInt(times[2])+"s");
        this.arrivalPoint = arrivalPoint;
        this.departurePoint = departurePoint;
        this.type = type;


        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date dep = format.parse(this.departureTime);
            Date arri = format.parse(hr + ":" + minute + ":" + "00");
            long difference = arri.getTime() - dep.getTime();
            difference = difference/1000;
            long min = Math.abs((difference/60)%60);
            long hour = Math.abs(difference/60/60);
            timeToDeparture = ((hour == 0)?"":hour + "t") + ((min == 0)?"":min+"m");
        } catch (Exception e) {

        }
        try {
            switch (Integer.parseInt(type)) {
                case 1: {
                    this.line = "Flybuss: " + line;
                    break;
                }case 2: {
                    this.line = "Buss: " + line;
                    break;
                }case 3: {
                    this.line = "Erstatning: " + line;
                    break;
                }case 4: {
                    this.line = "Flytog: " + line;
                    break;
                }case 5: {
                    this.line = "Båt: " + line;
                    break;
                }case 6: {
                    this.line = "Tog: " + line;
                    break;
                }case 7: {
                    this.line = "Trikk: " + line;
                    break;
                }case 8: {
                    this.line = "T-bane: " + line;
                    break;
                }
            }
        } catch (Exception e) {
            this.type = null;
            this.line = "Gå";
        }

    }

    @Override
    public String toString() {
        return (type == null)?line+": "+totaltime:departurePoint +" -> "+ arrivalPoint + "\n" +
                        line+ " (Avreise: " + timeToDeparture +" - Reisetid " + totaltime + ")";
    }
}
