package com.example.ensardz.yupivoyenrique.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ensardz.yupivoyenrique.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponentes();
    }

    public void initComponentes(){

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

    }
}
