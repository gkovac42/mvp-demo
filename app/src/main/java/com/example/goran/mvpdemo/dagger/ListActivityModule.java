package com.example.goran.mvpdemo.dagger;


import com.example.goran.mvpdemo.articles.list.ListActivity;
import com.example.goran.mvpdemo.articles.list.ListContract;
import com.example.goran.mvpdemo.articles.list.ListPresenter;
import com.example.goran.mvpdemo.data.DataInteractor;
import com.example.goran.mvpdemo.data.Interactor;
import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ApiHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 19.12.2017..
 */

@Module
public class ListActivityModule {

    private ListActivity listActivity;

    public ListActivityModule(ListActivity activity) {
        this.listActivity = activity;
    }

    @Provides
    @ActivityScope
    ListContract.View provideView() {
        return listActivity;
    }

    @Provides
    @ActivityScope
    ListContract.Presenter providePresenter(ListContract.View view, Interactor interactor) {
        return new ListPresenter(view, interactor);
    }

    @Provides
    @ActivityScope
    Interactor provideInteractor(DatabaseHelper databaseHelper, SharedPrefsHelper sharedPrefsHelper, ApiHelper apiHelper) {
        return new DataInteractor(databaseHelper, sharedPrefsHelper, apiHelper);
    }

}
