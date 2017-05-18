package com.example.ensardz.yupivoyenrique;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragments.FormularioAvion;
import fragments.FormularioHotel;
import fragments.FormularioHotelAvion;
import fragments.MenuPrincipal;

public class FormulariosContainer extends AppCompatActivity {

    private Fragment mHotelFragment;
    private Fragment mAvionFragment;
    private Fragment mHotelAvionFragment;

    private int idFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios_container);

        if (savedInstanceState!= null){
            onRestoreInstanceState(savedInstanceState);
        }

        //Recibe la informacion extra del MenuPrincipal
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idFragment = bundle.getInt(MenuPrincipal.APP_KEY);

        //TODO: Aqui regresa null cuando cambia de orientacion, buscar como retener el fragment para que no se borren
        //TODO: los contenidos de los EditText
        switch (idFragment){
            case MenuPrincipal.FRAGMENT_ID_HOTEL:
                if(mHotelFragment == null){
                    mHotelFragment = new FormularioHotel();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mHotelFragment).commit();
                }
                break;
            case MenuPrincipal.FRAGMENT_ID_AVION:
                if (mAvionFragment == null){
                    mAvionFragment = new FormularioAvion();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mAvionFragment).commit();
                }
                break;
            case MenuPrincipal.FRAGMENT_ID_HOTEL_AVION:
                if (mHotelAvionFragment == null){
                    mHotelAvionFragment = new FormularioHotelAvion();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mHotelAvionFragment).commit();
                }
                break;
            default:
                break;
        }
    }
}
