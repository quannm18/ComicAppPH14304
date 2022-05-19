package com.quannm18.comicappph14304.ui.dashboard;

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
import com.quannm18.comicappph14304.controllers.CategoryAdapter;
import com.quannm18.comicappph14304.models.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView rcvCategory;
    private List<Category> categoryList;
    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvCategory = (RecyclerView) view.findViewById(R.id.rcvCategory);
        categoryList = new ArrayList<>();
        fetchData();
        categoryAdapter = new CategoryAdapter(categoryList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        rcvCategory.setAdapter(categoryAdapter);
        rcvCategory.setLayoutManager(linearLayoutManager);
    }

    private void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://"+ ServerPort.SV +":3000/api/categories";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String id = jsonObject.getString("_id");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                String idCode = jsonObject.getString("idCode");
                                Category category = new Category(id, name, image, idCode);

                                categoryList.add(category);
                                categoryAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (getContext()==null){
                            return;
                        }
                        Toast.makeText(getContext(), "Loi" + error.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", error + "");
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}