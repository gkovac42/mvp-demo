package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.MyApp;
import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ApiHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Goran on 19.12.2017..
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(MyApp myApp);

    SharedPrefsHelper sharedPrefsHelper();

    DatabaseHelper databaseHelper();

    ApiHelper apiHelper();

    ListActivitySubcomponent listActivitySubcomponent(ListActivityModule listActivityModule);

    ArticleActivitySubcomponent articleActivitySubcomponent(ArticleActivityModule articleActivityModule);


}
