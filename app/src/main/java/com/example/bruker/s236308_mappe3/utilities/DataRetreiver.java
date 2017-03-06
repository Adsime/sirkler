package com.example.bruker.s236308_mappe3.utilities;

import com.example.bruker.s236308_mappe3.models.QuickTrip;
import com.example.bruker.s236308_mappe3.models.TravelProposal;
import com.example.bruker.s236308_mappe3.models.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruker on 20.11.2016.
 */

public class DataRetreiver {

    public static final String STAGES = "Stages";
    public static final String ARRIVAL_STOP = "ArrivalStop";
    public static final String DEPARTURE_STOP = "DepartureStop";
    public static final String NAME = "Name";
    public static final String TOTAL_TRAVEL_TIME = "TotalTravelTime";
    public static final String DEPARTURE_TIME = "DepartureTime";
    public static final String ARRIVAL_TIME = "ArrivalTime";

    public static List<Trip> getProposalsData(JSONArray array, int fromID, int toID) {
        List<Trip> proposals = new ArrayList<>();
        try {
            JSONObject holder, stage, arrival, departure;
            JSONArray stages;
            TravelProposal proposal;
            for(int i = 0; i < array.length(); i++) {
                Trip trip;
                List<TravelProposal> nestedList = new ArrayList<>();
                holder = array.getJSONObject(i);
                stages = holder.getJSONArray("Stages");
                for(int j = 0; j < stages.length(); j++) {
                    stage = stages.getJSONObject(j);
                    try {
                        arrival = stage.getJSONObject("ArrivalStop");
                        departure = stage.getJSONObject("DepartureStop");
                        proposal = new TravelProposal(stage.getString("LineName"), stage.getString("Transportation"),
                                stage.getString("TravelTime"), stage.getString("DepartureTime"), arrival.getString("Name"), departure.getString("Name"));
                        nestedList.add(proposal);
                    } catch (Exception ex) {
                        proposal = new TravelProposal("Gå", null, stage.getString("WalkingTime"), stage.getString("DepartureTime"), null, null);
                        nestedList.add(proposal);
                    }
                }
                trip = new Trip(nestedList, holder.getString("TotalTravelTime"), holder.getString("DepartureTime"), holder.getString("ArrivalTime"), fromID, toID);
                proposals.add(trip);
            }

        } catch (Exception e) {

        }
        return proposals;
    }

    public static QuickTrip getData(JSONArray array) {
        try {
            JSONObject first = array.getJSONObject(0);
            JSONArray stages = first.getJSONArray(STAGES);
            JSONObject intermediate = stages.getJSONObject(0);
            JSONObject departureStop = intermediate.getJSONObject(DEPARTURE_STOP);
            JSONObject arrivalStop = intermediate.getJSONObject(ARRIVAL_STOP);

            QuickTrip qt = new QuickTrip(departureStop.getString(NAME), arrivalStop.getString(NAME), first.getString(TOTAL_TRAVEL_TIME),
                    first.getString(DEPARTURE_TIME).split("T")[1], first.getString(ARRIVAL_TIME).split("T")[1]);
            for(int i = 1; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                qt.addAlternativeItem(new StringBuilder().append("Avreise: ").append(o.getString(DEPARTURE_TIME))
                        .append("Ankomst: ").append(o.getString(ARRIVAL_TIME)).toString());
            }
            return qt;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Stop> getStopsData(JSONArray array) {
        List<Stop> stops = new ArrayList<>();
        try {
            for(int i = 0; i < array.length() && stops.size() < 5; i++) {
                JSONObject o = array.getJSONObject(i);
                JSONArray a = o.getJSONArray("Stops");
                for(int j = 0; j < a.length(); j++) {
                    JSONObject fin = a.getJSONObject(j);
                    String s = fin.getString("PlaceType");
                    if(s.contains("Stop")) {
                        stops.add(new Stop(fin));
                    }
                }
            }
        } catch (Exception e) {
            try {
                for (int i = 0; i < array.length() && stops.size() < 5; i++) {
                    JSONObject fin = array.getJSONObject(i);
                    String s = fin.getString("PlaceType");
                    if(s.contains("Stop")) {
                        stops.add(new Stop(fin));
                    }
                }
            } catch (Exception ex) {

            }
        }
        return stops;
    }
}
