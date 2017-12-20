package com.example.goran.mvpdemo.dagger;

import com.example.goran.mvpdemo.articles.single.ArticleActivity;

import dagger.Component;

/**
 * Created by Goran on 19.12.2017..
 */

@Component (dependencies = AppComponent.class, modules = ArticleActivityModule.class)
@ActivityScope
public interface ArticleActivityComponent {

    void inject(ArticleActivity articleActivity);

}
