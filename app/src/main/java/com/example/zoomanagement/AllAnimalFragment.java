package com.example.zoomanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Model.Animal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllAnimalFragment extends Fragment {
    public static String document;
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAnimalFragment addAnimalFragment = new AddAnimalFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, addAnimalFragment).commit();
            }
        });
        GetAllAnimal();

        return v;
    }

    private void GetAllAnimal(){
        db.collection("Animals").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Animal animal = doc.toObject(Animal.class);
                                animal.setDocument(doc.getId());
                                lAnimal.add(animal);
                            }
                            animalAdapter = new AnimalAdapter(getActivity(), lAnimal);
                            listView.setAdapter(animalAdapter);

                        }
                    }
                });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Animal animal = lAnimal.get(i);
            document = animal.getDocument();
            EditAnimalFragment editAnimalFragment = new EditAnimalFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, editAnimalFragment).commit();
        });
    }
}