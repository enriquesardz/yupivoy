package com.example.ensardz.yupivoyenrique.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ensardz.yupivoyenrique.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();

        startActivity(new Intent(this, LoginActivity.class));

    }
}
