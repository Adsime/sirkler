package com.example.bruker.s236308_mappe3.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.models.TravelProposal;
import com.example.bruker.s236308_mappe3.models.Trip;

import java.util.List;

/**
 * Created by bruker on 30.11.2016.
 */

public class TravelProposalAdapter extends RecyclerView.Adapter<TravelProposalAdapter.Proposalholder> {

    private List<Trip> trips;
    private Context context;

    public TravelProposalAdapter(List<Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
    }

    @Override
    public Proposalholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_travelproposal, parent, false);
        return new Proposalholder(view);
    }

    public void update(List<Trip> proposals) {
        this.trips = proposals;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Proposalholder holder, int position) {
        Trip trip = trips.get(position);
        List<TravelProposal> proposals  = trip.getProposals();
        TextView stage, content;
        LinearLayout linearLayout;
        holder.infoHolder.removeAllViews();
        for(int i = 1; i <= proposals.size(); i++) {
            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(1f);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            stage = new TextView(context);
            stage.setTextColor(context.getResources().getColor(R.color.black));
            stage.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.2f));
            content = new TextView(context);
            content.setTextColor(context.getResources().getColor(R.color.black));
            content.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f));
            if(i%2==0) {
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
            }
            stage.setText("Steg " + i);
            content.setText(proposals.get((i-1)).toString());
            linearLayout.addView(stage);
            linearLayout.addView(content);
            holder.infoHolder.addView(linearLayout);
        }
        holder.headeing.setText("Reiseforslag - " + trip.getTotalTime() + "\nAvreise: " + trip.getDeparture() + " - Ankomst: " + trip.getArrival());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    class Proposalholder extends RecyclerView.ViewHolder {

        TextView headeing;
        LinearLayout infoHolder;

        public Proposalholder(View itemView) {
            super(itemView);
            headeing = (TextView) itemView.findViewById(R.id.heading);
            infoHolder = (LinearLayout) itemView.findViewById(R.id.TravelProposalInfo);
        }
    }
}
