package com.example.goran.mvpdemo.dagger;

import android.content.Context;

import com.example.goran.mvpdemo.data.DataInteractor;
import com.example.goran.mvpdemo.data.Interactor;
import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 13.12.2017..
 */

@Module (includes = ContextModule.class)
public class InteractorModule {

    @Provides
    Interactor provideDataInteractor(DatabaseHelper databaseHelper, SharedPrefsHelper sharedPrefsHelper) {
        return new DataInteractor(databaseHelper, sharedPrefsHelper);
    }

    @Provides
    DatabaseHelper provideDatabaseHelper(Context context) {
        return DatabaseHelper.getInstance(context);
    }

    @Provides
    SharedPrefsHelper provideSharedPrefsHelper(Context context) {
        return SharedPrefsHelper.getInstance(context);
    }


}
