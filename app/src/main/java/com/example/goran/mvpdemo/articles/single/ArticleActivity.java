package com.example.goran.mvpdemo.articles.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.goran.mvpdemo.MyApp;
import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.dagger.ArticleActivityModule;
import com.example.goran.mvpdemo.data.Article;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import javax.inject.Inject;


public class ArticleActivity extends AppCompatActivity implements ArticleContract.View, ViewPager.OnPageChangeListener {

    private ActionBar actionBar;
    private ArticlePagerAdapter adapter;
    private ViewPager viewPager;

    @Inject
    ArticleContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_reader);

        initUI();

        (((MyApp) getApplication()).getAppComponent())
                .articleActivitySubcomponent(new ArticleActivityModule(this))
                .inject(this);

        presenter.getArticleData();

    }

    private void initUI() {

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new ArticlePagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void updateArticles(ArrayList<Article> articles) {

        adapter.setDataSource(articles);
        adapter.notifyDataSetChanged();

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        actionBar.setTitle(articles.get(position).getTitle());
        viewPager.setCurrentItem(position);
    }

    @Override
    public void updateTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onPageSelected(int position) {
        presenter.onArticleSelected(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
