package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.articles.single.ArticleActivity;

import dagger.Subcomponent;

/**
 * Created by Goran on 19.12.2017..
 */


@Subcomponent(modules = ArticleActivityModule.class)
@ActivityScope
public interface ArticleActivitySubcomponent {

    void inject(ArticleActivity articleActivity);

}
