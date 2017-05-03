package com.example.ensardz.yupivoyenrique;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragments.FormularioHotel;
import fragments.FormularioHotelAvion;

public class FormulariosContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios_container);
        Fragment fragment = new FormularioHotelAvion();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
