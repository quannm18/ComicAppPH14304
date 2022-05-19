package com.quannm18.comicappph14304.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.quannm18.comicappph14304.MainActivity;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;
import com.quannm18.comicappph14304.controllers.ComicAdapter;
import com.quannm18.comicappph14304.models.Comic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView rcvComicHome;
    private List<Comic> comicList;
    private ComicAdapter comicAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvComicHome = (RecyclerView) view.findViewById(R.id.rcvComicHome);
        comicList = new ArrayList<>();
        fetchData();
        comicAdapter = new ComicAdapter(comicList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvComicHome.setAdapter(comicAdapter);
        rcvComicHome.setLayoutManager(linearLayoutManager);
    }
    private void fetchData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://"+ ServerPort.SV +":3000/api/getAll/comics";
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
                                comicAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Loi"+error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Error",error+"");
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}