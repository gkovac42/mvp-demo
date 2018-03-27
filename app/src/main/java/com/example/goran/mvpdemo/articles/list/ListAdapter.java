package com.example.goran.mvpdemo.articles.list;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goran.mvpdemo.R;
import com.example.goran.mvpdemo.data.model.Article;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by Goran on 8.11.2017..
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Article> articles;
    private ItemClickListener listener;

    public  ListAdapter() {
        this.articles = new ArrayList<>();
    }

    public void setDataSource(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public interface ItemClickListener {
        void onClick(int position);
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView listItem = (CardView) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardView listItem = holder.listItem;

        TextView txtTitle = listItem.findViewById(R.id.txt_item_title);
        SimpleDraweeView imgThumb = listItem.findViewById(R.id.img_item_thumb);

        txtTitle.setText(articles.get(position).getTitle());

        Uri imageUri = Uri.parse(articles.get(position).getUrlToImage());
        imgThumb.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView listItem;

        ViewHolder(CardView cv) {
            super(cv);

            listItem = cv;
            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });
        }
    }
}