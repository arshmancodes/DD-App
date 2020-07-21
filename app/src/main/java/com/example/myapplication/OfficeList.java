package com.example.myapplication;

import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class OfficeList extends AppCompatActivity {

    GridView gridView;
    ArrayList<HomeItem> list;
    ListAdapter adapter;
    String BaseURL = "https://noobistani.000webhostapp.com/DD%20app/";
    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_list);

        gridView = (GridView) findViewById(R.id.gridviewoffice);
        list = new ArrayList<HomeItem>();
        adapter = new ListAdapter(this, R.layout.row_layout, list);
        gridView.setAdapter(adapter);

        fetchimages();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String toSpeak = list.get(position).getName();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        //============Getting the data from SQLite===================

//        Cursor cursor = OfficeActivity.sqLiteHelper.getData("SELECT * FROM OFFICE");
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
    public void fetchimages()
    {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://noobistani.000webhostapp.com/DD%20app/getOffice.php", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++)
                {
                    try {
                        JSONObject object = (JSONObject) response.get(i);

                        String name = object.getString("name");
                        String desc = object.getString("description");
                        String URL = object.getString("image_url");
                        int id = object.getInt("id");

                        HomeItem item = new HomeItem(id, name, desc, BaseURL+URL);
                        list.add(item);
                        adapter.notifyDataSetChanged();


                        Toast.makeText(OfficeList.this, BaseURL+URL, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

//
    }
}
