package com.example.bruker.s236308_mappe3.fragments;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bruker.s236308_mappe3.R;
import com.example.bruker.s236308_mappe3.interfaces.Listener;

public class MenuFragment extends Fragment {

    private Listener listener;
    private ImageButton first, second, third, fourth, fifth;
    private boolean deletemode;

    public MenuFragment(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        deletemode = false;
        first = (ImageButton) view.findViewById(R.id.first);
        second = (ImageButton) view.findViewById(R.id.second);
        third = (ImageButton) view.findViewById(R.id.third);
        fourth = (ImageButton) view.findViewById(R.id.fourth);
        fifth = (ImageButton) view.findViewById(R.id.fifth);
        first.setImageResource(R.mipmap.back_button);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, listener.BACK_BUTTON);
            }
        });
        return view;
    }

    public void setQuickTripMenu(boolean editable) {
        clear();
        if(editable) {
            fifth.setImageResource(R.mipmap.save_icon);
            fifth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(null, listener.QUICK_TRIP_SAVE);
                }
            });
        }
    }

    public void setQuickTripListMenu() {
        clear();
        fifth.setImageResource(R.mipmap.delete_icon);
        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, listener.LIST_DELETE_MODE);
            }
        });
        fourth.setImageResource(R.mipmap.refresh_icon);
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(null, listener.REFRESH_LIST);
            }
        });
    }

    public void deleteMode() {
        deletemode = !deletemode;
        fifth.setImageResource((deletemode)?R.mipmap.save_icon:R.mipmap.delete_icon);
    }

    public void setHubMenu() {
        clear();
    }

    public void clear() {
        second.setImageResource(0);
        second.setOnClickListener(null);
        third.setImageResource(0);
        third.setOnClickListener(null);
        fourth.setImageResource(0);
        fourth.setOnClickListener(null);
        fifth.setImageResource(0);
        fifth.setOnClickListener(null);
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
