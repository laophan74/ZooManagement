package com.example.zoomanagement;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {
    private CardView animal;
    private CardView export;
    private CardView user;
    AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
    AllExportFragment allExportFragment = new AllExportFragment();
    AllAccountFragment allAccountFragment = new AllAccountFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        animal =(CardView) v.findViewById(R.id.animal);
        export =(CardView) v.findViewById(R.id.export);
        user =(CardView) v.findViewById(R.id.account);

        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allExportFragment).commit();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAccountFragment).commit();
            }
        });
        return v;
    }
}