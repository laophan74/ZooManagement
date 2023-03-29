package com.example.zoomanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Model.Animal;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllAnimal extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RelativeLayout add;
    private ListView listView;
    private AnimalAdapter animalAdapter;
    private ArrayList<Animal> lAnimal = new ArrayList<>();
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_animal);

        listView = findViewById(R.id.listview);
        add = findViewById(R.id.add);
        back = findViewById(R.id.backPress);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}