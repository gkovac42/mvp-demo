package com.example.goran.mvpdemo.articles.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.articles.single.ArticleActivity;
import com.example.goran.mvpdemo.data.Article;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements ListContract.View {

    private ProgressBar progressBar;
    private ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.main_progress_bar);

        presenter = new ListPresenter(ListActivity.this);

        presenter.getArticleData();
    }

    @Override
    public void showArticleList(ArrayList<Article> articles) {

        ListAdapter adapter = new ListAdapter(articles);
        adapter.setListener(new ListAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                presenter.onArticleClick(position);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToArticle(int position, ArrayList<Article> articleData) {

        Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("articles", articleData);
        startActivity(intent);
    }

    @Override
    public void showErrorDialog() {

        View dialogView = this.getLayoutInflater().inflate(R.layout.dialog_error, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(dialogView);

        final AlertDialog dlgError = builder.create();

        Button btnDialog = dialogView.findViewById(R.id.btn_dialog);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlgError.dismiss();
            }
        });

        dlgError.show();
        progressBar.setVisibility(View.GONE);
    }
}
