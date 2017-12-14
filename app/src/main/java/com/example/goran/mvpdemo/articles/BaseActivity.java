package com.example.goran.mvpdemo.articles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.goran.mvpdemo.dagger.ActivityComponent;
import com.example.goran.mvpdemo.dagger.ContextModule;
import com.example.goran.mvpdemo.dagger.DaggerActivityComponent;
import com.example.goran.mvpdemo.dagger.InteractorModule;
import com.example.goran.mvpdemo.data.Interactor;
import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Inject;

/**
 * Created by Goran on 13.12.2017..
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Interactor dataInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);

        getComponent().inject(this);

        getLifecycle().addObserver(dataInteractor);

    }

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .contextModule(new ContextModule(this.getApplication()))
                .interactorModule(new InteractorModule())
                .build();
    }

    public Interactor getDataInteractor() {
        return this.dataInteractor;
    }
}
