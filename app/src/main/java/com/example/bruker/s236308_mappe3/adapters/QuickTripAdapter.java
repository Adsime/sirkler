package com.example.bruker.s236308_mappe3.adapters;

import android.icu.text.RelativeDateTimeFormatter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.database.ModelTrip;
import com.example.bruker.s236308_mappe3.models.QuickTrip;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.RedusedTravelProposal;
import com.example.bruker.s236308_mappe3.models.TravelProposal;
import com.example.bruker.s236308_mappe3.models.Trip;

import java.util.List;

/**
 * Created by bruker on 20.11.2016.
 */

public class QuickTripAdapter extends RecyclerView.Adapter<QuickTripAdapter.QuickTripHolder> {

    private Listener listener;
    private List<RedusedTravelProposal> trips;
    private boolean deleteMode;

    public QuickTripAdapter(List<RedusedTravelProposal> trips, Listener listener) {
        this.listener = listener;
        this.trips = trips;
        this.deleteMode = false;
    }

    @Override
    public QuickTripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_quicktrip, parent, false);
        return new QuickTripHolder(view);
    }

    public void update(List<RedusedTravelProposal> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(QuickTripHolder holder, final int position) {
        final RedusedTravelProposal trip = trips.get(position);
        holder.arrival.setText(trip.arrival);
        holder.departure.setText(trip.departure);
        holder.traveltime.setText(trip.totalTime);
        holder.fromPlace.setText(trip.from);
        holder.toPlace.setText(trip.to);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(trip, listener.DELETE_QUICK_TRIP);
            }
        });
        if(deleteMode) {
            holder.delete.setImageResource(R.mipmap.delete_icon);
            holder.delete.setClickable(true);
        } else {
            holder.delete.setImageResource(0);
            holder.delete.setClickable(false);
        }
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onClick(null, listener.LIST_DELETE_MODE);
                return true;
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(trip, listener.OPEN_QUICKTRIP);
            }
        });
    }

    public void setDeleteMode() {
        deleteMode = !deleteMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class QuickTripHolder extends RecyclerView.ViewHolder {
        int id;
        CardView cardView;
        TextView toPlace, fromPlace, departure, arrival, traveltime;
        ImageView travelType;
        ImageButton delete;

        public QuickTripHolder(View view) {
            super(view);
            this.cardView = (CardView) view.findViewById(R.id.quicktripcard);
            this.toPlace = (TextView) view.findViewById(R.id.toPlace);
            this.fromPlace = (TextView) view.findViewById(R.id.fromPlace);
            this.departure = (TextView) view.findViewById(R.id.departure);
            this.arrival = (TextView) view.findViewById(R.id.arrival);
            this.traveltime = (TextView) view.findViewById(R.id.traveltime);
            this.travelType = (ImageView) view.findViewById(R.id.travelType);
            this.delete = (ImageButton) view.findViewById(R.id.quicktripdelete);
        }
    }

}
