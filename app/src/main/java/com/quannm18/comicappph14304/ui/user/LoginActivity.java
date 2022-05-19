package com.quannm18.comicappph14304.ui.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilEmailLogin;
    private TextInputLayout tilPasswordLogin;
    private MaterialButton btnLogin;
    private TextView tvLinkRegister;
    private AlertDialog alertDialog;
    private User user;
    private CheckBox chkRemember;
    private SharedPreferences sharedPreferences;
    private CheckBox chkOnceLogin;


    private String saveEmail;
    private String saveName;
    private String saveRole;
    private String saveID;
    private String savePass;
    private String saveToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmailLogin = (TextInputLayout) findViewById(R.id.tilEmailLogin);
        tilPasswordLogin = (TextInputLayout) findViewById(R.id.tilPasswordLogin);
        btnLogin = (MaterialButton) findViewById(R.id.btnLogin);
        tvLinkRegister = (TextView) findViewById(R.id.tvLinkRegister);
        chkRemember = (CheckBox) findViewById(R.id.chkRemember);
        chkOnceLogin = (CheckBox) findViewById(R.id.chkOnceLogin);
        sharedPreferences = getSharedPreferences("LoginUser",MODE_PRIVATE);

        if (!sharedPreferences.getString("token","").isEmpty()){
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        tilEmailLogin.getEditText().setText(sharedPreferences.getString("email",""));
        tilPasswordLogin.getEditText().setText(sharedPreferences.getString("password",""));
        chkRemember.setChecked(sharedPreferences.getBoolean("check",false));
        chkOnceLogin.setChecked(sharedPreferences.getBoolean("once",false));
        if (chkOnceLogin.isChecked()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        tvLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tilEmailLogin.getEditText().getText().toString();
                String password = tilPasswordLogin.getEditText().getText().toString();

                if (!(email.trim().length()==0 || password.trim().length()==0)){

                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    String url = "http://" + ServerPort.SV + ":3000/api_users/user/login";
                    Log.e("link", url);
                    StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                            url,
                            new com.android.volley.Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {
                                        if (chkRemember.isChecked()){
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            JSONObject userObj ;
                                            try {
                                                userObj= new JSONObject(response);
                                                JSONObject jsonObject = userObj.getJSONObject("user");
//                                                JSONObject tokenObj = userObj.getString("token");
                                                saveEmail = jsonObject.getString("email");
                                                saveName = jsonObject.getString("name");
                                                saveRole = jsonObject.getString("role");
                                                saveID = jsonObject.getString("_id");
                                                savePass = jsonObject.getString("password");
                                                saveToken = userObj.getString("token");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            editor.putString("email",saveEmail+"");
                                            editor.putString("name",saveName+"");
                                            editor.putString("role",saveRole+"");
                                            editor.putString("id",saveID+"");
                                            editor.putString("token",saveToken+"");
                                            editor.putString("password",tilPasswordLogin.getEditText().getText()+"");
                                            editor.putBoolean("check",true);
                                            editor.putBoolean("once",chkOnceLogin.isChecked());
                                            editor.apply();
                                        }else {
                                            if (sharedPreferences!=null){
                                                sharedPreferences.edit().clear().apply();
                                            }
                                        }
                                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("Email or password is wrong!");
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
                            String email = tilEmailLogin.getEditText().getText().toString();
                            String password = tilPasswordLogin.getEditText().getText().toString();
                            params.put("email", email+"");
                            params.put("password", password+"");
                            return params;
                        }

                    };

                    queue.add(jsonObjReq);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
            }
        });
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = tilEmailLogin.getEditText().getText().toString();
//                String password = tilPasswordLogin.getEditText().getText().toString();
//                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
//                String url = "http://"+ ServerPort.SV +":3000/api/login/"+username+"/"+password;
//                Log.e("link",url);
//                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                        new Response.Listener<JSONArray>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                try {
//                                    if (response.getJSONObject(0).getString("status").contains("W")){
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                        builder.setTitle("Notification");
//                                        builder.setMessage(""+response.getJSONObject(0).getString("status"));
//                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                alertDialog.dismiss();
//                                            }
//                                        });
//                                        alertDialog = builder.create();
//                                        alertDialog.show();
//                                    }else {
//                                        JSONObject object = response.getJSONObject(0);
//                                        JSONObject userObj = object.getJSONObject("user");
//                                        String _id= userObj.getString("_id");
//                                        String name = userObj.getString("name");
//                                        String email = userObj.getString("email");
//                                        String password = userObj.getString("password");
//                                        String role = userObj.getString("role");
//                                        user = new User(_id,name,email,password,role);
//                                        Log.e("user",userObj.toString());
//                                        if (chkRemember.isChecked()){
//                                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                                            editor.putString("email",email+"");
//                                            editor.putString("name",name+"");
//                                            editor.putString("role",role+"");
//                                            editor.putString("id",_id+"");
//                                            editor.putString("password",tilPasswordLogin.getEditText().getText()+"");
//                                            editor.putBoolean("check",true);
//                                            editor.putBoolean("once",chkOnceLogin.isChecked());
//                                            editor.apply();
//                                        }else {
//                                            if (sharedPreferences!=null){
//                                                sharedPreferences.edit().clear().apply();
//                                            }
//                                        }
//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.putExtra("user",user);
//                                        startActivity(intent);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(LoginActivity.this, "Loi"+error.toString(), Toast.LENGTH_SHORT).show();
//                                Log.e("Error",error+"");
//                            }
//                        });
//                requestQueue.add(jsonArrayRequest);
//
//            }
//        });

    }
}