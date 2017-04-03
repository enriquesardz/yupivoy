package com.example.ensardz.yupivoyenrique;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Contacto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().setTitle(R.string.contacto_titulo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
    }
}
