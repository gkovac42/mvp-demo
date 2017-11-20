package com.example.goran.mvpdemo.articles.single;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 8.11.2017..
 */

public class ArticlePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Article> articles;

    public ArticlePagerAdapter(FragmentManager fm, ArrayList<Article> articles) {
        super(fm);
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