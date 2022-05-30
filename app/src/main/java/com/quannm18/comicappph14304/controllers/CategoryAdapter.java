package com.quannm18.comicappph14304.controllers;

import android.content.Intent;
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
import com.quannm18.comicappph14304.models.Category;
import com.quannm18.comicappph14304.models.Comic;
import com.quannm18.comicappph14304.ui.detail.CateDetailActivity;
import com.quannm18.comicappph14304.ui.detail.DetailActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cate,null,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        final Category comic = categoryList.get(position);
//kj
        String imgUrl = "http://"+ ServerPort.SV+":3000/"+comic.getImage().substring(comic.getImage().indexOf("uploads"),comic.getImage().length());
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .into(holder.imgComicRow);
        holder.tvComicAuthorRow.setText(comic.getIdCode());

        holder.tvComicRow.setText(comic.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CateDetailActivity.class);
                intent.putExtra("category",comic);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    protected class CategoryHolder extends RecyclerView.ViewHolder {
        private ImageView imgComicRow;
        private TextView tvComicRow;
        private TextView tvComicAuthorRow;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);

            imgComicRow = (ImageView) itemView.findViewById(R.id.imgComicRow);
            tvComicRow = (TextView) itemView.findViewById(R.id.tvComicRow);
            tvComicAuthorRow = (TextView) itemView.findViewById(R.id.tvComicAuthorRow);

        }
    }
}
