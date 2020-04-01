package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<HomeItems> {

    private Context context;
    private List<HomeItems> list;

    public HomeAdapter(Context context, List<HomeItems> homeitemss)
    {
        super(context, R.layout.row_layout, homeitemss);
        this.context = context;
        this.list = homeitemss;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.row_layout, parent, false);

        TextView hName = convertView.findViewById(R.id.hName);
        TextView hQty = convertView.findViewById(R.id.hQty);
        TextView hitemchar = convertView.findViewById(R.id.hChar);

        hName.setText(list.get(position).getItemname());
        hQty.setText(list.get(position).getItemquantity());


        return convertView;
    }
}
