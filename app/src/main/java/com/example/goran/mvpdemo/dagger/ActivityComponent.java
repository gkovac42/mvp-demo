package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.articles.BaseActivity;

import dagger.Component;

/**
 * Created by Goran on 13.12.2017..
 */

@Component(modules = InteractorModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

}
