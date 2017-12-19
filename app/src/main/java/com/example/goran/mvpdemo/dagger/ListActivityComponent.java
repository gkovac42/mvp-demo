package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.articles.list.ListActivity;
import com.example.goran.mvpdemo.articles.list.ListPresenter;

import dagger.Component;

/**
 * Created by Goran on 19.12.2017..
 */

@Component (dependencies = AppComponent.class, modules = ListActivityModule.class)
@ActivityScope
public interface ListActivityComponent {

    void inject(ListActivity listActivity);

    ListPresenter presenter();
}
