package com.example.bruker.s236308_mappe3;

import android.Manifest;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bruker.s236308_mappe3.database.Database;
import com.example.bruker.s236308_mappe3.database.ModelTrip;
import com.example.bruker.s236308_mappe3.fragments.EditQuickTripFragment;
import com.example.bruker.s236308_mappe3.fragments.HubFragment;
import com.example.bruker.s236308_mappe3.fragments.LocationFragment;
import com.example.bruker.s236308_mappe3.fragments.MenuFragment;
import com.example.bruker.s236308_mappe3.fragments.QuickTripListFragment;
import com.example.bruker.s236308_mappe3.fragments.TravelProposalListFragment;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.QuickTrip;
import com.example.bruker.s236308_mappe3.models.RedusedTravelProposal;
import com.example.bruker.s236308_mappe3.models.SimpleAddress;
import com.example.bruker.s236308_mappe3.models.Trip;
import com.example.bruker.s236308_mappe3.utilities.DataRetreiver;
import com.example.bruker.s236308_mappe3.utilities.JsonRequester;
import com.example.bruker.s236308_mappe3.utilities.Stop;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Listener,
        ConnectionCallbacks, OnConnectionFailedListener {



    JsonRequester requester;
    private EditQuickTripFragment editQuickTripFragment;
    private HubFragment hubFragment;
    private MenuFragment menuFragment;
    private QuickTripListFragment quickTripListFragment;
    private LocationFragment locationFragment;
    private Fragment active;
    private Database db;
    private List<RedusedTravelProposal> redusedList;
    private List<ModelTrip> supposedTrips;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Geocoder geocoder;
    List<Address> addresses;
    private ProgressDialog progressDialog;
    private int supposedSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requester = new JsonRequester(this);
        editQuickTripFragment = new EditQuickTripFragment(this);
        menuFragment = new MenuFragment(this);
        hubFragment = new HubFragment(this);
        quickTripListFragment = new QuickTripListFragment(this);
        locationFragment = new LocationFragment(this);
        active = hubFragment;
        db = new Database(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Laster");
        progressDialog.setMessage("Vennligst vent...");
        progressDialog.setCancelable(false);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_top, android.R.anim.fade_out, R.anim.exit_to_top, android.R.anim.fade_in)
                .add(R.id.fragment_continer, hubFragment).add(R.id.menu_continer, menuFragment).commit();
        geocoder = new Geocoder(this, Locale.getDefault());
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle connectionHint) {

        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {

        }
    }


    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onPost(int tripID, int responseType, JSONArray arr) {
        switch (responseType) {
            case JsonRequester.CUSTOM_RESPONSE: {
                List<Stop> stops = DataRetreiver.getStopsData(arr);
                editQuickTripFragment.setAdapter(stops);
                break;
            }case QUICK_TRIP_SEARCH_RESPONSE: {
                progressDialog.dismiss();
                List<Trip> proposals = DataRetreiver.getProposalsData(arr, -1, -1);
                editQuickTripFragment.addTripSuggestoins(proposals);
                break;
            }case LIST_RESPONSE: {
                ModelTrip mt = supposedTrips.get(tripID);
                Trip t = DataRetreiver.getProposalsData(arr, mt.fromID, mt.toID).get(0);
                redusedList.add(new RedusedTravelProposal(mt.id, mt.from, mt.to, t.getDeparture(), t.getArrival(), t.getTotalTime(), mt.toID, mt.fromID));
                if(redusedList.size() == supposedSize) {
                    progressDialog.dismiss();
                    quickTripListFragment.setProposals(redusedList);
                }
                break;
            }case JsonRequester.CLOSE_RESPONSE: {
                List<Stop> stops = DataRetreiver.getStopsData(arr);
                List<SimpleAddress> simpleAddresses = new ArrayList<>();
                for(Stop s : stops) {
                    simpleAddresses.add(new SimpleAddress(s.getStopName(), s.getStopID()));
                }
                locationFragment.setAddresses(simpleAddresses);
                break;
            }
        }
    }
    @Override
    public void textInput(String input) {
        requester.getStop(input, JsonRequester.CUSTOM_RESPONSE);
    }

    private void initiateQuickTripList() {
        supposedTrips = db.getAll();
        supposedSize = supposedTrips.size();
        redusedList = new ArrayList<>();
        for(int i = 0; i < supposedSize; i++) {
            ModelTrip t = supposedTrips.get(i);
            Stop to = new Stop(t.toID);
            Stop from = new Stop(t.fromID);
            requester.getTravels(from, to, 1, LIST_RESPONSE, i);
        }
    }

    @Override
    public void onClick(Object o, int interaction) {
        switch (interaction) {
            case QUICK_TRIP_SEARCH: {
                progressDialog.show();
                editQuickTripFragment.search(requester, progressDialog);
                break;
            }case QUICK_TRIP_SAVE: {
                ModelTrip m = editQuickTripFragment.getTrip();
                if(m == null) {
                    Toast.makeText(this, "Ugyldige verdier!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(ModelTrip modelTrip : supposedTrips) {
                    if(modelTrip.from.equals(m.from) && modelTrip.to.equals(m.to)) {
                        Toast.makeText(this, "Denne ruten er allerede lagret!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                db.insertQuickTrip(m);
                supposedTrips = db.getAll();
                break;
            }case BACK_BUTTON: {
                onBackPressed();
                break;
            }case OPEN_QUICKTRIP: {
                active = editQuickTripFragment;
                menuFragment.setQuickTripMenu(o==null);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.exit_to_right, R.anim.enter_from_right, android.R.anim.fade_out)
                        .replace(R.id.fragment_continer, editQuickTripFragment).addToBackStack(null).commit();
                getSupportFragmentManager().executePendingTransactions();
                if(o != null && o instanceof  RedusedTravelProposal) {
                    editQuickTripFragment.fromTrip((RedusedTravelProposal) o);
                } else if(o != null && o instanceof SimpleAddress) {
                    editQuickTripFragment.fromLocation((SimpleAddress)o);
                    menuFragment.setQuickTripMenu(true);
                } else {
                    editQuickTripFragment.reset();
                }
                break;
            }case OPEN_SAVED_TRIPS: {
                progressDialog.show();
                initiateQuickTripList();
                active = quickTripListFragment;
                menuFragment.setQuickTripListMenu();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.exit_to_left, R.anim.enter_from_left, android.R.anim.fade_out)
                        .replace(R.id.fragment_continer, quickTripListFragment).addToBackStack(null).commit();
                break;
            }case OPEN_LOCATION: {
                active = locationFragment;
                mGoogleApiClient.reconnect();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, R.anim.exit_to_bottom, R.anim.enter_from_bottom, android.R.anim.fade_out)
                        .replace(R.id.fragment_continer, locationFragment).addToBackStack(null).commit();
                getFragmentManager().executePendingTransactions();
                try {
                    addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                    requester.getStop(addresses.get(0).getAddressLine(0), JsonRequester.CLOSE_RESPONSE);

                } catch (Exception e) {

                }
                break;
            }case LIST_DELETE_MODE: {
                menuFragment.deleteMode();
                quickTripListFragment.deleteMode();
                break;
            }case REFRESH_LIST: {
                progressDialog.show();
                initiateQuickTripList();
                break;
            }case DELETE_QUICK_TRIP: {
                db.delete(((RedusedTravelProposal)o).id);
                redusedList.remove(o);
                quickTripListFragment.setProposals(redusedList);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int entries = getSupportFragmentManager().getBackStackEntryCount();
        Fragment f = getSupportFragmentManager().getFragments().get(entries+1);
        if(f instanceof QuickTripListFragment) {
            menuFragment.setQuickTripListMenu();
        } else {
            menuFragment.setHubMenu();
        }
    }
}
