package com.example.goran.mvpdemo.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 13.12.2017..
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesContext() {
        return context;
    }
}
