package com.example.bruker.s236308_mappe3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.adapters.QuickTripAdapter;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.RedusedTravelProposal;

import java.util.ArrayList;
import java.util.List;

public class QuickTripListFragment extends Fragment {

    private RecyclerView rv;
    private List<RedusedTravelProposal> proposals;
    private QuickTripAdapter adapter;
    private Listener listener;

    public QuickTripListFragment(Listener listener) {
        this.listener = listener;
        proposals = new ArrayList<>();
    }

    public void setProposals(List<RedusedTravelProposal> proposals) {
        this.proposals = proposals;
        adapter.update(proposals);
    }

    public void deleteMode() {
        adapter.setDeleteMode();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quick_trip_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.view_cardholder);
        adapter = new QuickTripAdapter(proposals, listener);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        return view;
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
