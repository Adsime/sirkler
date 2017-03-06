package com.example.bruker.s236308_mappe3.fragments;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.adapters.LocationsAdapter;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.SimpleAddress;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {

    private Listener listener;
    private LocationsAdapter adapter;
    private RecyclerView rv;
    private List<SimpleAddress> addresses;

    public LocationFragment(Listener listener) {
        this.listener = listener;
        addresses = new ArrayList<>();
    }

    public void setAddresses(List<SimpleAddress> addresses) {
        this.addresses = addresses;
        if(adapter == null) {
            adapter = new LocationsAdapter(addresses, listener);
        }
        adapter.update(addresses);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        rv = (RecyclerView) view.findViewById(R.id.view_cardholder);
        adapter = new LocationsAdapter(addresses, listener);
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
