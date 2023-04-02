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
import android.widget.Toast;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Adapter.ExportAdapter;
import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.Export;
import com.example.zoomanagement.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllExportFragment extends Fragment {
    public static String document;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RelativeLayout add;
    private ListView listView;
    private ExportAdapter exportAdapter;
    private ArrayList<Export> lExport = new ArrayList<>();
    private ImageView back;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_export, container, false);
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
                AddExportFragment addExportFragment = new AddExportFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, addExportFragment).commit();
            }
        });
        GetAllExport();

        return v;
    }

    private void GetAllExport() {
        db.collection("Exports").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Export export = doc.toObject(Export.class);
                                export.setDocument(doc.getId());
                                lExport.add(export);
                            }
                            exportAdapter = new ExportAdapter(getActivity(), lExport);
                            listView.setAdapter(exportAdapter);

                        }
                    }
                });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Export export = lExport.get(i);
            document = export.getDocument();
            EditExportFragment editExportFragment = new EditExportFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, editExportFragment).commit();
        });
    }
}