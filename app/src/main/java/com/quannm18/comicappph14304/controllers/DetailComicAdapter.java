package com.quannm18.comicappph14304.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quannm18.comicappph14304.MainActivity;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;

import java.util.List;

public class DetailComicAdapter extends RecyclerView.Adapter<DetailComicAdapter.ComicHolder> {
    private List<String> comicList;

    public DetailComicAdapter(List<String> comicList) {
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ComicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicHolder holder, int position) {
        final String s = comicList.get(position);

        String imgUrl = "http:/"+ ServerPort.SV +":3000/"+s.substring(s.indexOf("uploads"),s.length());
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .into(holder.imgViewDetailRow);
    }


    @Override
    public int getItemCount() {
        return comicList.size();
    }
    protected class ComicHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewDetailRow;

        public ComicHolder(@NonNull View itemView) {
            super(itemView);

            imgViewDetailRow = (ImageView) itemView.findViewById(R.id.imgViewDetailRow);

        }
    }

}
