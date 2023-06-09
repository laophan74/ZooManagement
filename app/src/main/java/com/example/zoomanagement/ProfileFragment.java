package com.example.zoomanagement;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    private FirebaseAuth Auth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView userName;
    private TextView gender;
    private TextView birth;
    private TextView role;
    private TextView email;
    private TextView phone;
    private TextView address;
    private Button editBtn;
    private User user;
    private ImageView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        editBtn = v.findViewById(R.id.btnEdit);
        userName = v.findViewById(R.id.username);
        gender = v.findViewById(R.id.gender);
        birth = v.findViewById(R.id.birth);
        role = v.findViewById(R.id.role);
        email = v.findViewById(R.id.email);
        phone = v.findViewById(R.id.phone);
        address = v.findViewById(R.id.address);
        Auth = FirebaseAuth.getInstance();
        logout = v.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
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

    private void logOut() {
        Auth.signOut();
        startActivity(new Intent(getActivity(), LogIn.class));
    }

    private void EditBtnClick() {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, editProfileFragment).commit();
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
                            userName.setText(user.getUserName());
                            email.setText(user.getEmail());
                            address.setText(user.getAddress());
                            birth.setText(user.getBirth());
                            gender.setText(user.getGender());
                            role.setText(user.getRole());
                            phone.setText(user.getPhone());
                        }
                    }
                });
    }
}