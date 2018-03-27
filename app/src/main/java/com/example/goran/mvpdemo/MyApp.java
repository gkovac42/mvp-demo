package com.example.goran.mvpdemo;

import android.app.Application;

import com.example.goran.mvpdemo.dagger.AppComponent;
import com.example.goran.mvpdemo.dagger.AppModule;
import com.example.goran.mvpdemo.dagger.NetworkModule;
import com.example.goran.mvpdemo.data.remote.ApiHelper;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Goran on 19.12.2017..
 */

public class MyApp extends Application {

    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

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
