package com.example.bruker.s236308_mappe3.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.database.ModelTrip;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.RedusedTravelProposal;
import com.example.bruker.s236308_mappe3.models.SimpleAddress;
import com.example.bruker.s236308_mappe3.models.Trip;
import com.example.bruker.s236308_mappe3.utilities.DataRetreiver;
import com.example.bruker.s236308_mappe3.utilities.JsonRequester;
import com.example.bruker.s236308_mappe3.utilities.Stop;
import com.example.bruker.s236308_mappe3.models.TravelProposal;

import java.util.ArrayList;
import java.util.List;

public class EditQuickTripFragment extends Fragment {

    private AutoCompleteTextView fromPlace, toPlace;
    private Listener listener;
    private List<Stop> stops;
    private AdapterView.OnItemClickListener selector;
    private TextWatcher watcher;
    private Stop from, to;
    private LinearLayout search;
    private TravelProposalListFragment travelProposalListFragment;

    public EditQuickTripFragment() {

    }

    public EditQuickTripFragment(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        travelProposalListFragment = null;
        if(search != null) {
            search.performClick();
        }
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if (charSequence.length() != 0 && !s.contains("[tog]") && !s.contains("[T-bane]") && !s.contains("[buss]") && !s.contains("[trikk]")) {
                    listener.textInput(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        selector = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(fromPlace.isFocused()) {
                    from = stops.get(i);
                } else {
                    to = stops.get(i);
                }
            }
        };
    }

    public void addTripSuggestoins(List<Trip> proposals) {
        if(travelProposalListFragment == null || getFragmentManager().getFragments().size() < 1) {
            travelProposalListFragment = new TravelProposalListFragment();
            getFragmentManager().beginTransaction().add(R.id.tripSuggestionsContaner, travelProposalListFragment, "").commit();
            travelProposalListFragment.setProposals(proposals);
            return;
        }
        travelProposalListFragment.setProposals(proposals);
        travelProposalListFragment.update();
    }

    public ModelTrip getTrip() {
        if(from != null && to != null) {
            return new ModelTrip(-1, from.getStopName(), to.getStopName(), from.getStopID(), to.getStopID());
        }
        return null;
    }

    public void reset() {
        from = null;
        to = null;
        fromPlace.setText("");
        toPlace.setText("");
    }

    public void fromTrip(RedusedTravelProposal rtp) {
        fromPlace.setEnabled(false);
        toPlace.setEnabled(false);
        fromPlace.setText(rtp.from);
        toPlace.setText(rtp.to);
        to = new Stop(rtp.toID);
        from = new Stop(rtp.fromID);
        search.performClick();
    }

    public void fromLocation(SimpleAddress address) {
        this.from = new Stop(address.fromID);
        this.fromPlace.setText(address.from);
    }

    public void search(JsonRequester requester, ProgressDialog progressDialog) {
        if(from != null && to != null) {
            requester.getTravels(from, to, 5, Listener.QUICK_TRIP_SEARCH_RESPONSE, -1);
        } else {
            progressDialog.dismiss();
        }
    }

    public void setAdapter(List<Stop> stops) {
        this.stops = stops;
        ArrayAdapter<Stop> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, stops);

        if(fromPlace.isFocused()) {
            fromPlace.setAdapter(adapter);
            if(fromPlace.getText().length() > 0) {
                fromPlace.showDropDown();
            }
        } else {
            toPlace.setAdapter(adapter);
            if(toPlace.getText().length() > 0) {
                toPlace.showDropDown();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_quick_trip, container, false);
        this.fromPlace = (AutoCompleteTextView) view.findViewById(R.id.fromPlace);
        this.toPlace = (AutoCompleteTextView) view.findViewById(R.id.toPlace);
        this.search = (LinearLayout) view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, listener.QUICK_TRIP_SEARCH);
            }
        });

        fromPlace.addTextChangedListener(watcher);
        fromPlace.setOnItemClickListener(selector);
        toPlace.addTextChangedListener(watcher);
        toPlace.setOnItemClickListener(selector);
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
