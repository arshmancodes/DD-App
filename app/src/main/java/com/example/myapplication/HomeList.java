package com.example.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class HomeList extends AppCompatActivity {

    GridView gridView;
    ArrayList<HomeItem> list;
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);

        gridView = (GridView) findViewById(R.id.gridview);
        list = new ArrayList<HomeItem>();
        adapter = new ListAdapter(this, R.layout.row_layout, list);
        gridView.setAdapter(adapter);

        //============Getting the data from SQLite===================

//        Cursor cursor = HomeActivity.sqLiteHelper.getData("SELECT * FROM HOME");
//        list.clear();
//        while (cursor.moveToNext())
//        {
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            String desc = cursor.getString(2);
//            byte[] image = cursor.getBlob(3);
//
//            list.add(new HomeItem(id, name, desc, image));
//        }
//        adapter.notifyDataSetChanged();
    }
}
