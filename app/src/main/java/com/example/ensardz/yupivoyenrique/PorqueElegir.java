package com.example.ensardz.yupivoyenrique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PorqueElegir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().setTitle(R.string.porque_elegir_titulo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porque_elegir);
    }
}
