package com.example.ensardz.yupivoyenrique.Activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ensardz.yupivoyenrique.Activities.Fragments.FormularioAvion;
import com.example.ensardz.yupivoyenrique.Activities.Fragments.FormularioHotel;
import com.example.ensardz.yupivoyenrique.Activities.Fragments.FormularioHotelAvion;
import com.example.ensardz.yupivoyenrique.Activities.Fragments.MenuPrincipal;
import com.example.ensardz.yupivoyenrique.R;

public class FormulariosContainerActivity extends AppCompatActivity {

    private Fragment mHotelFragment;
    private Fragment mAvionFragment;
    private Fragment mHotelAvionFragment;


    private int idFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios_container);

        //Recibe la informacion extra del MenuPrincipal
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idFragment = bundle.getInt(MenuPrincipal.APP_KEY);

        //TODO: La actividad retiene el estado pero solamente ded HotelAvion
        if (savedInstanceState == null){
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
}
