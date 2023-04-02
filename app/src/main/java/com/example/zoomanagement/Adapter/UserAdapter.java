package com.example.zoomanagement.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zoomanagement.Model.Animal;
import com.example.zoomanagement.Model.User;
import com.example.zoomanagement.R;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(context, R.layout.cardview_account, null);
        }

        TextView name = view.findViewById(R.id.name);
        TextView birth = view.findViewById(R.id.birth);
        TextView phone = view.findViewById(R.id.phone);
        TextView address = view.findViewById(R.id.address);
        TextView gender = view.findViewById(R.id.gender);
        TextView email = view.findViewById(R.id.email);

        User user = users.get(i);

        name.setText(user.getUserName());
        birth.setText("Birth: "+user.getBirth());
        phone.setText("Phone: "+user.getPhone());
        address.setText("Address: "+user.getAddress());
        gender.setText("Gender: "+user.getGender());
        email.setText("Email: "+user.getEmail());
        return view;
    }
}
