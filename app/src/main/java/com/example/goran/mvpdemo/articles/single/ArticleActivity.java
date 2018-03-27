package com.example.goran.mvpdemo.articles.single;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.goran.mvpdemo.MyApp;
import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.dagger.ArticleActivityModule;
import com.example.goran.mvpdemo.data.model.Article;

import java.util.ArrayList;

import javax.inject.Inject;


public class ArticleActivity extends AppCompatActivity implements ArticleContract.View, ViewPager.OnPageChangeListener {

    private static final String EXTRA_POSITION = "position";

    private ArticlePagerAdapter adapter;
    private ViewPager viewPager;

    @Inject
    ArticleContract.Presenter presenter;

    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        (((MyApp) getApplication()).getAppComponent())
                .articleActivitySubcomponent(new ArticleActivityModule(this))
                .inject(this);

        initUI();

        presenter.getArticleData();

    }

    private void initUI() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(articles.get(position).getTitle());
        }

        viewPager.setCurrentItem(position);
    }

    @Override
    public void updateTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
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
