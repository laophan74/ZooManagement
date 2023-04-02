package com.example.zoomanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditAnimalFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText size;
    private EditText weight;
    private EditText origin;
    private EditText status;
    private Animal animal;
    private Button editBtn;
    private ImageView back;
    private RelativeLayout delete;
    private User staffName;
    private Spinner staff;
    private ArrayList<User> lUser = new ArrayList<>();
    private ArrayList<String> lDist1 = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_animal, container, false);
        editBtn = v.findViewById(R.id.editBtn);
        delete = v.findViewById(R.id.delete);
        back = v.findViewById(R.id.backPress);

        name = v.findViewById(R.id.name);
        size = v.findViewById(R.id.size);
        weight = v.findViewById(R.id.weight);
        origin = v.findViewById(R.id.origin);
        status = v.findViewById(R.id.status);
        staff = v.findViewById(R.id.staff);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBtnClick();
            }
        });
        Load();
        loadStaff();
        return v;
    }

    private void Delete() {
        AlertDialog.Builder altd = new AlertDialog.Builder(getActivity());
        altd.setMessage("Do you want to delete this?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Animals").document(AllAnimalFragment.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
                                    }
                                });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alert = altd.create();
        alert.setTitle("Warning!!!");
        alert.show();
    }

    private void EditBtnClick() {
        String Name = name.getText().toString();
        String Origin = origin.getText().toString();
        String Size = size.getText().toString();
        String Weight = weight.getText().toString();
        String Status = status.getText().toString();
        String Staff = staffName.getEmail();

        Map<String, Object> docData = new HashMap<>();
        docData.put("name", Name);
        docData.put("origin", Origin);
        docData.put("size", Float.parseFloat(Size));
        docData.put("weight", Float.parseFloat(Weight));
        docData.put("status", Status);
        docData.put("staff", Staff);
        docData.put("picture", "null");

        db.collection("Animals").document(AllAnimalFragment.document)
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        AllAnimalFragment allAnimalFragment = new AllAnimalFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAnimalFragment).commit();
                    }
                });
    }

    private void Load() {
        db.collection("Animals").document(AllAnimalFragment.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            animal = doc.toObject(Animal.class);
                            name.setText(animal.getName());
                            origin.setText(animal.getOrigin());
                            weight.setText(String.valueOf(animal.getWeight()));
                            size.setText(String.valueOf(animal.getSize()));
                            status.setText(animal.getStatus());

                        }
                    }
                });
    }
    private void loadStaff() {
        db.collection("Users").get()
                .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    User user = doc.toObject(User.class);
                                    lUser.add(user);
                                }
                                for (User item : lUser) {
                                    lDist1.add(item.getUserName());
                                }
                                ArrayAdapter<String> aaC1 = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_spinner_dropdown_item, lDist1);
                                staff.setAdapter(aaC1);
                                staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        staffName = ReturnUser(staff.getSelectedItem().toString());
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });

                            }
                        }
                );
    }
    private User ReturnUser(String x) {
        for (User item : lUser) {
            if (Objects.equals(x, item.getUserName())) return item;
        }
        return null;
    }
}