package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    EditText name, desc;
    Button add, view, upload;
    ImageView img;
    String upload_image = "https://noobistani.000webhostapp.com/DD%20app/uploadHomeImage.php";
    Bitmap imageBitmap;
    private final int IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(imageBitmap!= null && !name.equals("") && !desc.equals("")){
                    uploadImage();
                }else{
                    Toast.makeText(HomeActivity.this, "Please enter all details!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeList.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(HomeActivity.this.getContentResolver(), path);
                img.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }



    void uploadImage(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upload_image, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(HomeActivity.this, response, Toast.LENGTH_SHORT).show();
                name.setText("");
                desc.setText("");
                img.setImageResource(R.mipmap.ic_launcher);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Network_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Server_error_ksa), Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Auth_Failure_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Parse_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Connection_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(HomeActivity.this, getString(R.string.Timeout_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, getString(R.string.Something_went_wrong_ksa), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("img", imageToString(imageBitmap));
                params.put("name", name.getText().toString());
                params.put("description", desc.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                2,
                2));

        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        queue.add(stringRequest);
    }

    private void init()
    {
        name = findViewById(R.id.homeitemname);
        desc = findViewById(R.id.homeitemtext);
        add = findViewById(R.id.addhomeitem);
        view = findViewById(R.id.viewhomelist);
        upload = findViewById(R.id.homeuploadbtn);
        img = findViewById(R.id.homeimg);
    }
}
