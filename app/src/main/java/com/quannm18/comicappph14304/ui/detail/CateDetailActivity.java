package com.quannm18.comicappph14304.ui.detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;
import com.quannm18.comicappph14304.controllers.ComicAdapter;
import com.quannm18.comicappph14304.models.Category;
import com.quannm18.comicappph14304.models.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CateDetailActivity extends AppCompatActivity {
    private TextView tvNameCateDetail;
    private RecyclerView rcvCateDetail;
    private ComicAdapter adapter;
    private List<Comic> comicList;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra("category");
        setContentView(R.layout.activity_cate_detail);
        tvNameCateDetail = (TextView) findViewById(R.id.tvNameCateDetail);
        rcvCateDetail = (RecyclerView) findViewById(R.id.rcvCateDetail);

        tvNameCateDetail.setText(category.getName()+" List");
        comicList = new ArrayList<>();
        fetchData(category.getIdCode());
        CountDownTimer countDownTimer = new CountDownTimer(1000,500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                if (comicList.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CateDetailActivity.this);
                    builder.setTitle("Notify");
                    builder.setMessage("This object has no data");
                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }.start();
        adapter = new ComicAdapter(comicList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(CateDetailActivity.this,2);
        rcvCateDetail.setLayoutManager(linearLayoutManager);
        rcvCateDetail.setAdapter(adapter);
    }

    void fetchData(String params){
        RequestQueue requestQueue = Volley.newRequestQueue(CateDetailActivity.this);
        String url = "http://"+ ServerPort.SV+":3000/api/categories/"+params;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                List<String> stringList = new ArrayList<>();
                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("_id");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                String author = jsonObject.getString("author");
                                JSONArray ctgArray = jsonObject.getJSONArray("category");
                                String category = ctgArray.getString(0);
                                try {
                                    JSONArray image_detail = jsonObject.getJSONArray("image_detail");
                                    if (image_detail.length()==0){
                                        stringList.add("Null");
                                    }else {
                                        for (int j = 0; j < image_detail.length(); j++) {
                                            stringList.add(String.valueOf(image_detail.getString(j)));
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                Comic comic = new Comic(id,name,image,author,category,  stringList);

                                comicList.add(comic);
                                adapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(CateDetailActivity.this, "Loi"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}