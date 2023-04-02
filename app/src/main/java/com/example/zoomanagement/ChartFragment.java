package com.example.zoomanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zoomanagement.Adapter.AnimalAdapter;
import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.Export;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    private PieChart pieChart;
    private ArrayList<PieEntry> entries= new ArrayList<>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        pieChart = v.findViewById((R.id.chart));
        entries.clear();

        loadPieChartData();
        setUpPieChart();

        return v;
    }

    private void setUpPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Export Chart");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
    }

    private void loadPieChartData() {
        db.collection("Exports").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){
                                Export export = doc.toObject(Export.class);
                                entries.add(new PieEntry((float) export.getQuantity(), export.getMaterialName()));
                            }

                            ArrayList<Integer> colors= new ArrayList<>();
                            for (int color: ColorTemplate.MATERIAL_COLORS){
                                colors.add(color);
                            }
                            for (int color: ColorTemplate.VORDIPLOM_COLORS){
                                colors.add(color);
                            }

                            PieDataSet dataSet = new PieDataSet(entries, "");
                            dataSet.setColors(colors);

                            PieData data = new PieData(dataSet);
                            data.setDrawValues(true);
                            data.setValueFormatter(new PercentFormatter(pieChart));
                            data.setValueTextSize(12f);
                            data.setValueTextColor(Color.BLACK);

                            pieChart.setData(data);
                            pieChart.invalidate();

                        }
                    }
                });
    }
}