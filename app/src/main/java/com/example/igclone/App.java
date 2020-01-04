package com.example.igclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("kZoZx3gkQYjFMrv1qoZ473kz56resUh0y2qOxvIg")
                // if defined
                .clientKey("KZ7ulM96kfg0thsN24PCS0huTlmDzNkfp0a9mnWB")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
