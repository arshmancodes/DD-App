package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    private EditText user, pass;
    private Button login, forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        login = findViewById(R.id.pSign);
        user = findViewById(R.id.pUser);
        forgotPass = findViewById(R.id.forgotPass);
        pass = findViewById(R.id.pPass);
        login = findViewById(R.id.pSign);
        TextView sign = (TextView) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                if(user.getText().toString().trim().equals("") || pass.getText().toString().trim().equals("")){
                    Toast.makeText(LoginActivity.this, "Please enter your credentials first...", Toast.LENGTH_SHORT).show();
                    user.setError("Required!");
                    pass.setError("Required!");
                }else{
                    final String email = user.getText().toString().trim();
                    final String password = pass.getText().toString().trim();
                    showProgress(true);

                    String url = "https://noobistani.000webhostapp.com/DD%20app/user_login.php?user_email="+email+"&user_password="+password;
                    loginUser(url);

                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void clickit(View view)
    {
        Intent intent = new Intent(LoginActivity.this, com.example.myapplication.RegisterActivity.class);
        startActivity(intent);
    }
    public void hideKeyBoard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loginUser(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress(false);
                if (response.equals("0 results")) {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                } else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    // LOGIC FOR EXTRACTING USER DATA FROM LOGIN RESPONSE AND SETTING IT TO LOGGED IN USER OBJECT
                    String substring = "";
                    String[] data = new String[5];
                    int count = 0;
                    int counter = 0;
                    for (int i = 0; i < response.length(); i++) {
                        if (count > 0 && response.charAt(i) != ',') {
                            substring = substring + response.charAt(i);
                        }
                        if (response.charAt(i) == ',' || i == response.length() - 1) {
                            data[counter] = substring;
                            counter++;
                            count = 0;
                            substring = "";
                        } else if (response.charAt(i) == ':') {
                            count++;
                        }
                    }
                    // SAVING DATA IN SHARED PREFERENCES
                    SharedPref.savePreferencesBoolean("isLoggedIn", true, LoginActivity.this);
                    SharedPref.savePreferencesInt("user_id", Integer.parseInt(data[0]), LoginActivity.this);
                    SharedPref.savePreferences("user_name", data[1], LoginActivity.this);
                    SharedPref.savePreferences("user_email", data[2], LoginActivity.this);
                    SharedPref.savePreferences("user_category", data[3], LoginActivity.this);

                    Intent dashBoard = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(dashBoard);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                if (error instanceof NetworkError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Network_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Server_error_ksa), Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Auth_Failure_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Parse_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Connection_error), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(LoginActivity.this, getString(R.string.Timeout_error), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.Something_went_wrong_ksa), Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }
}
