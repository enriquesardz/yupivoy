package com.example.ensardz.yupivoyenrique;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragments.FormularioHotel;
import fragments.FormularioHotelAvion;

public class FormulariosContainer extends AppCompatActivity {

    private static final String TAG_HOTEL_AVION_FRAGMENT = "hotel_avion_fragment";
    private Fragment mHotelAvionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios_container);
        if (savedInstanceState!= null){
            onRestoreInstanceState(savedInstanceState);
        }
        //TODO: Aqui regresa null cuando cambia de orientacion, buscar como retener el fragment para que no se borren
        //TODO: los contenidos de los EditText
        if (mHotelAvionFragment == null){
            mHotelAvionFragment = new FormularioHotelAvion();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mHotelAvionFragment, TAG_HOTEL_AVION_FRAGMENT).commit();
        }

    }
}
