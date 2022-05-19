package com.quannm18.comicappph14304.controllers;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;
import com.quannm18.comicappph14304.models.Comic;
import com.quannm18.comicappph14304.ui.detail.DetailActivity;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicHolder> {
    private List<Comic> comicList;

    public ComicAdapter(List<Comic> comicList) {
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ComicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comic,null,false);
        return new ComicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicHolder holder, int position) {
        final Comic comic = comicList.get(position);

        String imgUrl = "http://"+ ServerPort.SV+":3000/"+comic.getImage().substring(comic.getImage().indexOf("uploads"),comic.getImage().length());
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .into(holder.imgComicRow);
        holder.tvComicAuthorRow.setText(comic.getAuthor());

        holder.tvComicRow.setText(comic.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("comic",comic);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    protected class ComicHolder extends RecyclerView.ViewHolder {
        private ImageView imgComicRow;
        private TextView tvComicRow;
        private TextView tvComicAuthorRow;

        public ComicHolder(@NonNull View itemView) {
            super(itemView);

            imgComicRow = (ImageView) itemView.findViewById(R.id.imgComicRow);
            tvComicRow = (TextView) itemView.findViewById(R.id.tvComicRow);
            tvComicAuthorRow = (TextView) itemView.findViewById(R.id.tvComicAuthorRow);

        }
    }
}
