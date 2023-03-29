package com.example.zoomanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAnimalFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText origin;
    private EditText size;
    private EditText weight;
    private EditText status;
    private Button add;
    private ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_animal, container, false);
        name = v.findViewById(R.id.name);
        origin = v.findViewById(R.id.origin);
        size = v.findViewById(R.id.size);
        weight = v.findViewById(R.id.weight);
        status = v.findViewById(R.id.status);
        add = v.findViewById(R.id.addBtn);
        back = v.findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn();
            }
        });

        return v;
    }

    private void addBtn() {
        String Name = name.getText().toString();
        String Origin = origin.getText().toString();
        String Size = size.getText().toString();
        String Weight = weight.getText().toString();
        String Status = status.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("name", Name);
        docData.put("origin", Origin);
        docData.put("size", Float.parseFloat(Size));
        docData.put("weight", Float.parseFloat(Weight));
        docData.put("status", Status);
        docData.put("picture", "null");


        db.collection("Animals").document()
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
                    }
                });
    }
}