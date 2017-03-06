package com.example.bruker.s236308_mappe3.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.interfaces.Listener;
import com.example.bruker.s236308_mappe3.models.SimpleAddress;

import java.util.List;

/**
 * Created by bruker on 04.12.2016.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.AddressHolder> {

    List<SimpleAddress> addresses;
    Listener listener;

    public LocationsAdapter(List<SimpleAddress> addresses, Listener listener) {
        this.listener = listener;
        this.addresses = addresses;
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, int position) {
        final SimpleAddress address = addresses.get(position);
        holder.addressContainer.setText(address.from);
        holder.suggestion.setText("Forslag " + (position+1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(address, Listener.OPEN_QUICKTRIP);
            }
        });
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_address, parent, false);
        return new AddressHolder(view);
    }

    public void update(List<SimpleAddress> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView suggestion, addressContainer;

        public AddressHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.locationcard);
            suggestion = (TextView) itemView.findViewById(R.id.suggestion);
            addressContainer = (TextView) itemView.findViewById(R.id.address_container);

        }
    }
}
