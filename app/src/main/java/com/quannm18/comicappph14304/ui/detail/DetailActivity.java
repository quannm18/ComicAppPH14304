package com.quannm18.comicappph14304.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.controllers.DetailComicAdapter;
import com.quannm18.comicappph14304.models.Comic;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RecyclerView rcvDetail;
    private DetailComicAdapter adapter;
    private List<String> comicList;
    private TextView textView4;
    private ImageView btnChange;
    private Boolean check;
    private ConstraintLayout layoutMainDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView4 = (TextView) findViewById(R.id.textView4);
        btnChange = (ImageView) findViewById(R.id.btnChange);
        layoutMainDetail = (ConstraintLayout) findViewById(R.id.layoutMainDetail);


        Comic comic = (Comic) getIntent().getSerializableExtra("comic");
        textView4.setText(comic.getName());
        comicList = new ArrayList<>();
        for (int i = 0; i < comic.getImage_detail().size(); i++) {
            comicList.add(String.valueOf(comic.getImage_detail().get(i)));
        }
        rcvDetail = (RecyclerView) findViewById(R.id.rcvDetail);
        adapter = new DetailComicAdapter(comicList);

        rcvDetail.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rcvDetail.setLayoutManager(layoutManager);
        check = false;
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check){
                    check = false;
                    btnChange.setImageResource(R.drawable.moon);
                    textView4.setTextColor(Color.BLACK);
                    layoutMainDetail.setBackgroundColor(Color.WHITE);
                }else {
                    check = true;
                    btnChange.setImageResource(R.drawable.sun);
                    textView4.setTextColor(Color.WHITE);
                    layoutMainDetail.setBackgroundColor(Color.parseColor("#540011"));
                }
            }
        });
    }
}