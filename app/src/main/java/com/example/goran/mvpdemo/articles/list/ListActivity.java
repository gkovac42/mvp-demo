package com.example.goran.mvpdemo.articles.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.goran.mvpdemo.MyApp;
import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.articles.dialog.ErrorDialogFragment;
import com.example.goran.mvpdemo.articles.single.ArticleActivity;
import com.example.goran.mvpdemo.dagger.DaggerListActivityComponent;
import com.example.goran.mvpdemo.dagger.ListActivityComponent;
import com.example.goran.mvpdemo.dagger.ListActivityModule;
import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.DataInteractor;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    private ProgressBar progressBar;
    private ListAdapter adapter;

    @Inject
    ListContract.Presenter presenter;

    @Inject
    DataInteractor interactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        initUI();

        ListActivityComponent component = DaggerListActivityComponent.builder()
                .appComponent(((MyApp) getApplication()).getAppComponent())
                .listActivityModule(new ListActivityModule(this))
                .build();

        component.inject(this);

        presenter = new ListPresenter(this, interactor);

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
