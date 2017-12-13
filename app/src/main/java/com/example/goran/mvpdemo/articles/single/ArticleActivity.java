package com.example.goran.mvpdemo.articles.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.articles.BaseActivity;
import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;


public class ArticleActivity extends BaseActivity implements ArticleContract.View, ViewPager.OnPageChangeListener {

    private ActionBar actionBar;
    private ArticlePagerAdapter adapter;
    private ViewPager viewPager;
    private ArticleContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        initUI();

        presenter = new ArticlePresenter(this, getDataInteractor());

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
