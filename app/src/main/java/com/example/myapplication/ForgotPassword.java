package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    EditText email;
    Button getEmail;
    String url = "http://noobistani.000webhostapp.com/DD%20app/forgotPassword.php?email=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.emailEditTxt);
        getEmail = findViewById(R.id.getBtn);

        getEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")){
                    Toast.makeText(ForgotPassword.this, "Please fulfill the field...", Toast.LENGTH_SHORT).show();
                    email.setError("Required!");
                }else{
                    getEmail(url+email.getText().toString());
                }
            }
        });
    }

    void getEmail(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Success")){
                    Toast.makeText(ForgotPassword.this, "An email has been sent to the respective email address.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ForgotPassword.this, "This email does not exists in our database!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Network_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Server_error_ksa), Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Auth_Failure_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Parse_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Connection_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Timeout_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPassword.this, getString(R.string.Something_went_wrong_ksa), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                2,
                2));

        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);
        queue.add(stringRequest);
    }

}