package com.example.zoomanagement.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.R;

import java.util.ArrayList;

public class AnimalAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Animal> animals;

    public AnimalAdapter(Context context, ArrayList<Animal> animals) {
        this.context = context;
        this.animals = animals;
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int i) {
        return animals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.cardview_animal, null);
        }

        TextView cname = view.findViewById(R.id.name);
        TextView origin = view.findViewById(R.id.origin);
        TextView size = view.findViewById(R.id.size);
        TextView weight = view.findViewById(R.id.weight);
        TextView status = view.findViewById(R.id.status);
        TextView staff = view.findViewById(R.id.staff);

        Animal animal = animals.get(i);

        cname.setText(animal.getName());
        origin.setText("Origin: "+animal.getOrigin());
        size.setText("Size: "+animal.getSize().toString());
        weight.setText("Weight: "+animal.getWeight().toString());
        status.setText("Status: "+animal.getStatus());
        staff.setText("Staff: "+animal.getStaff());
        return view;
    }
}
