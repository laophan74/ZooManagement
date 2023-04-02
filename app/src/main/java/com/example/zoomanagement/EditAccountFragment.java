package com.example.zoomanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAccountFragment extends Fragment {
    FirebaseAuth auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText name;
    private EditText birth;
    private EditText address;
    private EditText phone;
    private EditText gender;
    private User user;
    private Button editBtn;
    private ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_account, container, false);
        auth = FirebaseAuth.getInstance();
        editBtn = v.findViewById(R.id.editBtn);
        back = v.findViewById(R.id.backPress);

        name = v.findViewById(R.id.name);
        birth = v.findViewById(R.id.birth);
        address = v.findViewById(R.id.address);
        phone = v.findViewById(R.id.phone);
        gender = v.findViewById(R.id.gender);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllAccountFragment allAccountFragment = new AllAccountFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAccountFragment).commit();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBtnClick();
            }
        });
        Load();

        return v;
    }

    private void Load() {
        db.collection("Users").document(AllAccountFragment.document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            user = doc.toObject(User.class);
                            name.setText(user.getUserName());
                            birth.setText(user.getBirth());
                            address.setText(user.getAddress());
                            phone.setText(user.getPhone());
                            gender.setText(user.getGender());

                        }
                    }
                });
    }

    private void EditBtnClick() {
        String Name = name.getText().toString();
        String Birth = birth.getText().toString();
        String Address = address.getText().toString();
        String Phone = phone.getText().toString();
        String Gender = gender.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("userName", Name);
        docData.put("birth", Birth);
        docData.put("address", Address);
        docData.put("phone", Phone);
        docData.put("gender", Gender);

        db.collection("Users").document(AllAccountFragment.document)
                .update(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        AllAccountFragment allAccountFragment = new AllAccountFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, allAccountFragment).commit();
                    }
                });
    }

}