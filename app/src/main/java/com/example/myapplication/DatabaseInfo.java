package com.example.myapplication;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class DatabaseInfo extends Application {

    public static final String APPLICATION_ID = "2954C908-C4AA-E64B-FF90-9AEF49D9D400";
    public static final String API_KEY = "1E44FF82-F3D4-4DD3-9CA8-6929814E812D";
    public static final String SERVER_URL = "https://api.backendless.com";

   public static BackendlessUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
