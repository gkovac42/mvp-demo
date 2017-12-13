package com.example.goran.mvpdemo.articles.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.articles.BaseActivity;
import com.example.goran.mvpdemo.articles.dialog.ErrorDialogFragment;
import com.example.goran.mvpdemo.articles.single.ArticleActivity;
import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

public class ListActivity extends BaseActivity implements ListContract.View {

    private ProgressBar progressBar;
    private ListAdapter adapter;
    private ListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        presenter = new ListPresenter(this, getDataInteractor());

        presenter.getArticleData();
    }


    public void initUI() {

        progressBar = findViewById(R.id.main_progress_bar);

        adapter = new ListAdapter();
        adapter.setListener(position -> presenter.onArticleClick(position));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void updateArticles(ArrayList<Article> articles) {

        adapter.setDataSource(articles);

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToArticle(int position) {

        Intent intent = new Intent(ListActivity.this, ArticleActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void showErrorDialog() {

        ErrorDialogFragment newFragment = ErrorDialogFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), null);

        progressBar.setVisibility(View.GONE);
    }
}
