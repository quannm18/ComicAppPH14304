package com.quannm18.comicappph14304.ui.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.quannm18.comicappph14304.MainActivity;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;
import com.quannm18.comicappph14304.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassActivity extends AppCompatActivity {
    private TextInputLayout tilEmailChangePass;
    private TextInputLayout tilOldPasswordChangePass;
    private TextInputLayout tilNewPass;
    private MaterialButton btnChangePass;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        tilEmailChangePass = (TextInputLayout) findViewById(R.id.tilEmailChangePass);
        tilOldPasswordChangePass = (TextInputLayout) findViewById(R.id.tilOldPasswordChangePass);
        tilNewPass = (TextInputLayout) findViewById(R.id.tilNewPass);
        btnChangePass = (MaterialButton) findViewById(R.id.btnChangePass);

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = tilEmailChangePass.getEditText().getText().toString();
                String oldPassword = tilOldPasswordChangePass.getEditText().getText().toString();
                String newPassword = tilNewPass.getEditText().getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(ChangePassActivity.this);
                String url = "http://"+ ServerPort.SV +":3000/api/change_pass/"+username+"/"+oldPassword+"/"+newPassword;
                Log.e("link",url);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    if (response.getJSONObject(0).getString("status").contains("W")||response.getJSONObject(0).getString("status").contains("F")){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassActivity.this);
                                        builder.setTitle("Notification");
                                        builder.setMessage(""+response.getJSONObject(0).getString("status"));
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog = builder.create();
                                        alertDialog.show();
                                    }else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassActivity.this);
                                        builder.setTitle("Notification");
                                        builder.setMessage(""+response.getJSONObject(0).getString("status"));
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                finish();
                                                Intent intent = new Intent(ChangePassActivity.this,LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog = builder.create();
                                        alertDialog.show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChangePassActivity.this, "Loi"+error.toString(), Toast.LENGTH_SHORT).show();
                                Log.e("Error",error+"");
                            }
                        });
                requestQueue.add(jsonArrayRequest);
            }
        });
    }
}