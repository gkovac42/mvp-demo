package com.example.goran.mvpdemo;

import android.app.Application;

import com.example.goran.mvpdemo.dagger.*;
import com.example.goran.mvpdemo.data.remote.ApiHelper;

/**
 * Created by Goran on 19.12.2017..
 */

public class MyApp extends Application {

    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = com.example.goran.mvpdemo.dagger.DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .networkModule(new NetworkModule(ApiHelper.BASE_URL))
                .build();

        appComponent.inject(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
