package com.example.zoomanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Adapter.UserAdapter;
import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllAccountFragment extends Fragment {
    public static String document;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RelativeLayout add;
    private ListView listView;
    private UserAdapter userAdapter;
    private ArrayList<User> lUser = new ArrayList<>();
    private ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_account, container, false);
        listView = v.findViewById(R.id.listview);
        back = v.findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment).commit();
            }
        });

        GetAllAccount();

        return v;
    }

    private void GetAllAccount() {
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                User user = doc.toObject(User.class);
                                user.setDocument(doc.getId());
                                lUser.add(user);
                            }
                            userAdapter = new UserAdapter(getActivity(), lUser);
                            listView.setAdapter(userAdapter);

                        }
                    }
                });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            User user = lUser.get(i);
            document = user.getDocument();
            EditAccountFragment editAccountFragment = new EditAccountFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, editAccountFragment).commit();
        });
    }
}