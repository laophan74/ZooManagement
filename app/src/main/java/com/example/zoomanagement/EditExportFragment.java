package com.example.zoomanagement;

import android.content.DialogInterface;
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
import com.example.zoomanagement.Model.Export;
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

public class EditExportFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText unit;
    private EditText quantity;
    private EditText exportDate;
    private Export export;
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
        View v = inflater.inflate(R.layout.fragment_edit_export, container, false);
        editBtn = v.findViewById(R.id.editBtn);
        delete = v.findViewById(R.id.delete);
        back = v.findViewById(R.id.backPress);

        name = v.findViewById(R.id.name);
        unit = v.findViewById(R.id.unit);
        quantity = v.findViewById(R.id.quantity);
        exportDate = v.findViewById(R.id.exportDate);
        staff = v.findViewById(R.id.staff);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllExportFragment allExportFragment = new AllExportFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allExportFragment).commit();
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

    private void EditBtnClick() {
        String Name = name.getText().toString();
        String Unit = unit.getText().toString();
        String Quantity = quantity.getText().toString();
        String ExportDate = exportDate.getText().toString();
        String Staff = staffName.getEmail();

        Map<String, Object> docData = new HashMap<>();
        docData.put("materialName", Name);
        docData.put("unit", Unit);
        docData.put("quantity", Integer.parseInt(Quantity));
        docData.put("exportDate", ExportDate);
        docData.put("staff", Staff);

        db.collection("Exports").document(AllExportFragment.document)
                .update(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        AllExportFragment allExportFragment = new AllExportFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allExportFragment).commit();
                    }
                });
    }

    private void Load() {
        db.collection("Exports").document(AllExportFragment.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            export = doc.toObject(Export.class);
                            name.setText(export.getMaterialName());
                            unit.setText(export.getUnit());
                            quantity.setText(String.valueOf(export.getQuantity()));
                            exportDate.setText(export.getExportDate());

                        }
                    }
                });
    }

    private void Delete() {
        AlertDialog.Builder altd = new AlertDialog.Builder(getActivity());
        altd.setMessage("Do you want to delete this?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("Exports").document(AllExportFragment.document)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        AllExportFragment allAnimalFragment = new AllExportFragment();
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
}