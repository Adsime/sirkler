package com.example.bruker.s236308_mappe3.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.adapters.TravelProposalAdapter;
import com.example.bruker.s236308_mappe3.models.TravelProposal;
import com.example.bruker.s236308_mappe3.models.Trip;

import java.util.List;

public class TravelProposalListFragment extends Fragment {

    private RecyclerView rv;
    private TravelProposalAdapter adapter;
    private List<Trip> proposals;

    public TravelProposalListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setProposals(List<Trip> proposals) {
        this.proposals = proposals;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_proposal_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.view_cardholder);
        adapter = new TravelProposalAdapter(proposals, getContext());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        return view;
    }

    public void update() {
        adapter.update(proposals);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
