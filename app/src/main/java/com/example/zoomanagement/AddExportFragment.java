package com.example.zoomanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddExportFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText unit;
    private EditText quantity;
    private EditText exportDate;
    private Button add;
    private ImageView back;
    private Spinner staff;
    private User staffName;
    private ArrayList<User> lUser = new ArrayList<>();
    private ArrayList<String> lDist1 = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_export, container, false);
        name = v.findViewById(R.id.name);
        unit = v.findViewById(R.id.unit);
        quantity = v.findViewById(R.id.quantity);
        exportDate = v.findViewById(R.id.exportDate);
        add = v.findViewById(R.id.addBtn);
        back = v.findViewById(R.id.backPress);
        staff = v.findViewById(R.id.staff);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllExportFragment allExportFragment = new AllExportFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allExportFragment).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn();
            }
        });

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

    private void addBtn() {
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

        db.collection("Exports").document()
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        AllExportFragment allExportFragment = new AllExportFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allExportFragment).commit();
                    }
                });
    }
}