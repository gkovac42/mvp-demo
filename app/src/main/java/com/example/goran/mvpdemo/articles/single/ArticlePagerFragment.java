package com.example.goran.mvpdemo.articles.single;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.data.model.Article;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Goran on 13.11.2017..
 */

public class ArticlePagerFragment extends Fragment {

    public static ArticlePagerFragment newInstance(Article article) {

        Bundle args = new Bundle();
        args.putParcelable("article", article);

        ArticlePagerFragment fragment = new ArticlePagerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_pager, container,
                false);

        SimpleDraweeView imgHeader = rootView.findViewById(R.id.img_header);
        TextView txtTitle = rootView.findViewById(R.id.txt_title);
        TextView txtContent = rootView.findViewById(R.id.txt_content);

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

