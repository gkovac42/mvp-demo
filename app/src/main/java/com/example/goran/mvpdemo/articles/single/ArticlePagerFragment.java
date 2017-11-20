package com.example.goran.mvpdemo.articles.single;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.data.Article;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Goran on 13.11.2017..
 */

public class ArticlePagerFragment extends Fragment {

    private SimpleDraweeView imgHeader;
    private TextView txtTitle;
    private TextView txtContent;

    // new instance to simplify article passing
    public static ArticlePagerFragment newInstance(Article article) {
        ArticlePagerFragment fragment = new ArticlePagerFragment();
        Bundle args = new Bundle();
        args.putParcelable("article", article);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_pager, container,
                false);

        imgHeader = rootView.findViewById(R.id.img_header);
        txtTitle = rootView.findViewById(R.id.txt_title);
        txtContent = rootView.findViewById(R.id.txt_content);

        Article article = getArguments().getParcelable("article");

        if (article != null) {
            txtTitle.setText(article.getTitle());
            txtContent.setText(article.getContent());
            Uri imageUri = Uri.parse(article.getUrlToImage());
            imgHeader.setImageURI(imageUri);
        }

        return rootView;
    }
}

