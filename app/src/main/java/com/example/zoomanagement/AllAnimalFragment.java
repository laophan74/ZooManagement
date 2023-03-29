package com.example.zoomanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Model.Animal;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllAnimalFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RelativeLayout add;
    private ListView listView;
    private AnimalAdapter animalAdapter;
    private ArrayList<Animal> lAnimal = new ArrayList<>();
    private ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_animal, container, false);
        listView = v.findViewById(R.id.listview);
        add = v.findViewById(R.id.add);
        back = v.findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();

            }
        });

        return v;
    }
}