package com.example.goran.mvpdemo.articles.list;

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
import com.example.goran.mvpdemo.dagger.ListActivityModule;
import com.example.goran.mvpdemo.data.model.Article;

import java.util.ArrayList;

import javax.inject.Inject;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    private ProgressBar progressBar;
    private ListAdapter adapter;

    @Inject
    ListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (((MyApp) getApplication()).getAppComponent())
                .listActivitySubcomponent(new ListActivityModule(this))
                .inject(this);

        initUI();

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
        startActivity(ArticleActivity.newIntent(this, position));
    }

    @Override
    public void showErrorDialog() {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), null);
    }
}
