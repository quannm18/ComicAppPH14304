package com.quannm18.comicappph14304.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.ServerPort;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout tilNameRegister;
    private TextInputLayout tilEmailRegister;
    private TextInputLayout tilPasswordRegister;
    private TextInputLayout tilRetypeRegister;
    private MaterialButton btnRegister;
    private TextView tvLinkLogin;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilNameRegister = (TextInputLayout) findViewById(R.id.tilNameRegister);
        tilEmailRegister = (TextInputLayout) findViewById(R.id.tilEmailRegister);
        tilPasswordRegister = (TextInputLayout) findViewById(R.id.tilPasswordRegister);
        tilRetypeRegister = (TextInputLayout) findViewById(R.id.tilRetypeRegister);
        btnRegister = (MaterialButton) findViewById(R.id.btnRegister);
        tvLinkLogin = (TextView) findViewById(R.id.tvLinkLogin);

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
//            }
//        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = tilNameRegister.getEditText().getText().toString();
                String email = tilEmailRegister.getEditText().getText().toString();
                String password = tilPasswordRegister.getEditText().getText().toString();
                String rePassword = tilRetypeRegister.getEditText().getText().toString();
                if (username.trim().length() == 0 ||
                        email.trim().length() == 0 ||
                        password.trim().length() == 0 ||
                        rePassword.trim().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Please enter enough data!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    return;
                }
                if (!rePassword.equalsIgnoreCase(password)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Retype password is wrong!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                String url = "http://" + ServerPort.SV + ":3000/api_users/user/reg";
                Log.e("link", url);
                StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                        url,
                        new com.android.volley.Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setTitle("Success");
                                    builder.setMessage("Successfully!");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            alertDialog.dismiss();
                                        }
                                    });
                                    alertDialog = builder.create();
                                    alertDialog.show();
                                    return;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage("Account already exists!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog = builder.create();
                        alertDialog.show();
                        return;
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        Map<String, String> pars = new HashMap<String, String>();
                        pars.put("Content-Type", "application/x-www-form-urlencoded");
                        //return pars;
                        return "application/x-www-form-urlencoded";
                    }


                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        String username = tilNameRegister.getEditText().getText().toString();
                        String email = tilEmailRegister.getEditText().getText().toString();
                        String password = tilPasswordRegister.getEditText().getText().toString();
                        params.put("name", username+"");
                        params.put("email", email+"");
                        params.put("password", password+"");
                        return params;
                    }

                };

                queue.add(jsonObjReq);
            }
        });
        tvLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}