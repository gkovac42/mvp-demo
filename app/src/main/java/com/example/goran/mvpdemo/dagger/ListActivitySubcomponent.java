package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.articles.list.ListActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 19.12.2017..
 */

@Subcomponent(modules = ListActivityModule.class)
@ActivityScope
public interface ListActivitySubcomponent {

    void inject(ListActivity listActivity);
}
