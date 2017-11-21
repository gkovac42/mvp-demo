package com.example.goran.mvpdemo.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 21.11.2017..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "factory_news_db";
    public static final String TABLE_NAME = "articles";
    public static final String TITLE_COLUMN = "title";
    public static final String URL_COLUMN = "url";
    public static final String IMAGE_COLUMN = "image";
    public static final String CONTENT_COLUMN = "content";
    public static int DB_VERSION = 1;

    private static DatabaseHelper instance = null;

    // singleton to ensure a single instance of DatabaseHelper object
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    // save selected article to database
    public void insertArticles(ArrayList<Article> articles) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Article a : articles) {
            ContentValues articleValues = new ContentValues();
            articleValues.put(TITLE_COLUMN, a.getTitle());
            articleValues.put(URL_COLUMN, a.getUrl());
            articleValues.put(IMAGE_COLUMN, a.getUrlToImage());
            articleValues.put(CONTENT_COLUMN, a.getContent());

            db.insert(TABLE_NAME, null, articleValues);
        }
        db.close();
    }

    // get all articles from database
    public ArrayList<Article> getArticles() {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Article> articles = new ArrayList<>();
        Article article;

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{TITLE_COLUMN, URL_COLUMN, IMAGE_COLUMN, CONTENT_COLUMN},
                null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            article = new Article();
            article.setTitle(cursor.getString(0));
            article.setUrl(cursor.getString(1));
            article.setUrlToImage(cursor.getString(2));
            article.setContent(cursor.getString(3));
            articles.add(article);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return articles;
    }

    // remove all articles from database
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COLUMN + " TEXT, "
                + URL_COLUMN + " TEXT, "
                + IMAGE_COLUMN + " TEXT, "
                + CONTENT_COLUMN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
