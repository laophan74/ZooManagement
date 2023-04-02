package com.example.zoomanagement.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zoomanagement.Model.Export;
import com.example.zoomanagement.Model.User;
import com.example.zoomanagement.R;

import java.util.ArrayList;

public class ExportAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Export> exports;

    public ExportAdapter(Context context, ArrayList<Export> exports) {
        this.context = context;
        this.exports = exports;
    }

    @Override
    public int getCount() {
        return exports.size();
    }

    @Override
    public Object getItem(int i) {
        return exports.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.cardview_export, null);
        }

        TextView name = view.findViewById(R.id.name);
        TextView unit = view.findViewById(R.id.unit);
        TextView quantity = view.findViewById(R.id.quantity);
        TextView date = view.findViewById(R.id.exportDate);
        TextView staff = view.findViewById(R.id.staff);

        Export export = exports.get(i);

        name.setText(export.getMaterialName());
        unit.setText("Unit: "+export.getUnit());
        quantity.setText("Quantity: "+export.getQuantity());
        date.setText("Export date: "+export.getExportDate());
        staff.setText("staff: "+export.getStaff());
        return view;
    }
}
