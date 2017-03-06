package com.example.bruker.s236308_mappe3.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.interfaces.Listener;


public class HubFragment extends Fragment {

    private Button quicktrip, savedtrips, location;
    private Listener listener;

    public HubFragment(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub, container, false);
        quicktrip = (Button) view.findViewById(R.id.quicktrip);
        savedtrips = (Button) view.findViewById(R.id.savedtrips);
        location = (Button) view.findViewById(R.id.location);
        quicktrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, Listener.OPEN_QUICKTRIP);
            }
        });
        savedtrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, Listener.OPEN_SAVED_TRIPS);
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, Listener.OPEN_LOCATION);
            }
        });
 
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
