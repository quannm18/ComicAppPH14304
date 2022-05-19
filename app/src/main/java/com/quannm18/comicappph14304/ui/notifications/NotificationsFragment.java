package com.quannm18.comicappph14304.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.quannm18.comicappph14304.MainActivity;
import com.quannm18.comicappph14304.R;
import com.quannm18.comicappph14304.models.User;
import com.quannm18.comicappph14304.ui.user.ChangePassActivity;
import com.quannm18.comicappph14304.ui.user.LoginActivity;
import com.quannm18.comicappph14304.ui.user.RegisterActivity;

public class NotificationsFragment extends Fragment {
    private ImageView imgAvt;
    private TextView textView2;
    private TextView tvNamePersonal;
    private TextView tvEmailPersonal;
    private TextView tvRole;
    private MaterialButton btnLogout;
    private User user;
    private SharedPreferences sharedPreferences;
    private MaterialButton btnRegisterProfile;
    private MaterialButton btnChangePassPro;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgAvt = (ImageView) view.findViewById(R.id.imgAvt);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        tvNamePersonal = (TextView) view.findViewById(R.id.tvNamePersonal);
        tvEmailPersonal = (TextView) view.findViewById(R.id.tvEmailPersonal);
        tvRole = (TextView) view.findViewById(R.id.tvRole);
        btnLogout = (MaterialButton) view.findViewById(R.id.btnLogout);
        btnRegisterProfile = (MaterialButton) view.findViewById(R.id.btnRegisterProfile);
        btnChangePassPro = (MaterialButton) view.findViewById(R.id.btnChangePassPro);



//        user = ((MainActivity)getActivity()).user;

        //user = (User) getActivity().getIntent().getSerializableExtra("user");
        sharedPreferences = getActivity().getSharedPreferences("LoginUser",MODE_PRIVATE);
        Log.e("sj",sharedPreferences.toString());

        tvNamePersonal.setText(sharedPreferences.getString("name",""));
        tvEmailPersonal.setText("Email: "+sharedPreferences.getString("email",""));
        tvRole.setText("Role: "+sharedPreferences.getString("role",""));


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                sharedPreferences.edit().clear().apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnRegisterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnChangePassPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent = new Intent(getContext(), ChangePassActivity.class);
                startActivity(intent);
            }
        });

    }
}