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
import android.widget.Toast;

import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {
    private FirebaseAuth Auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText dmm;
    private EditText birth;
    private EditText address;
    private EditText phone;
    private EditText gender;
    private Button editBtn;
    private ImageView back;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        dmm = v.findViewById(R.id.dcmm);
        gender = v.findViewById(R.id.gender);
        birth = v.findViewById(R.id.birth);
        phone = v.findViewById(R.id.phone);
        address = v.findViewById(R.id.address);
        Auth = FirebaseAuth.getInstance();
        editBtn = v.findViewById(R.id.btnEdit);
        back = v.findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBtnClick();
            }
        });
        Load();

        return  v;
    }

    private void EditBtnClick() {
        FirebaseUser currentUser = Auth.getCurrentUser();
        String Name = dmm.getText().toString();
        String Gender = gender.getText().toString();
        String Birth = birth.getText().toString();
        String Phone = phone.getText().toString();
        String Address = address.getText().toString();

        Map<String, Object> docData = new HashMap<>();
        docData.put("userName", Name);
        docData.put("gender", Gender);
        docData.put("birth", Birth);
        docData.put("email", currentUser.getEmail());
        docData.put("phone", Phone);
        docData.put("address", Address);
        docData.put("role", "Admin");

        db.collection("Users").document(currentUser.getEmail())
                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        ProfileFragment profileFragment = new ProfileFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment).commit();
                    }
                });
    }

    private void Load() {
        FirebaseUser currentUser = Auth.getCurrentUser();
        db.collection("Users").document(currentUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            user = doc.toObject(User.class);
                            dmm.setText(user.getUserName());
                            address.setText(user.getAddress());
                            birth.setText(user.getBirth());
                            gender.setText(user.getGender());
                            phone.setText(user.getPhone());
                        }
                    }
                });
    }
}