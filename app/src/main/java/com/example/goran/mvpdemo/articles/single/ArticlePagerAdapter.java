package com.example.goran.mvpdemo.articles.single;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.goran.mvpdemo.data.model.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 8.11.2017..
 */

public class ArticlePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Article> articles;

    public ArticlePagerAdapter(FragmentManager fm) {
        super(fm);
        articles = new ArrayList<>();
    }

    public ArrayList<Article> getArticles() {
        return this.articles;
    }

    public void setDataSource(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public Fragment getItem(int position) {
        return ArticlePagerFragment.newInstance(articles.get(position));
    }

    @Override
    public int getCount() {
        return articles.size();
    }
}