package com.example.goran.mvpdemo.articles.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.data.Article;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;


public class ArticleActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_reader);

        // get articles data and position from main activity
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        articles = intent.getParcelableArrayListExtra("articles");

        // init pager adapter
        ArticlePagerAdapter articlePagerAdapter = new ArticlePagerAdapter(getSupportFragmentManager(), articles);

        // init view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(articlePagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(position);

        // set action bar back button and title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(articles.get(position).getTitle());
    }

    @Override
    public void onPageSelected(int position) {
        getSupportActionBar().setTitle(articles.get(position).getTitle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
