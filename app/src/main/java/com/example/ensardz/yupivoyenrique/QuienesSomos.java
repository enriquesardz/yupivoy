package com.example.ensardz.yupivoyenrique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class QuienesSomos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().setTitle(R.string.quienes_somos_titulo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quienes_somos);
    }
}
