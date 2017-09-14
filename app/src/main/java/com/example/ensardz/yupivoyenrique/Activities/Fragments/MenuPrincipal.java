package com.example.ensardz.yupivoyenrique.Activities.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ensardz.yupivoyenrique.Activities.FormulariosContainerActivity;
import com.example.ensardz.yupivoyenrique.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPrincipal extends Fragment {

    //La KEY de la aplicacion
    public static final String APP_KEY = "com.example.ensardz.yupivoyenrique.formularios";
    //ID's que se usan para saber que fragment se va a inflar.
    public static final int FRAGMENT_ID_HOTEL = 100;
    public static final int FRAGMENT_ID_AVION = 150;
    public static final int FRAGMENT_ID_HOTEL_AVION = 200;


    private Context mContext;

    private Button hotelButton;
    private Button avionButton;
    private Button hotelAvionButton;


    public MenuPrincipal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_menu_principal, container, false);
        mContext = getContext();
        initComponentes(view);

        return view;
    }

    private void initComponentes(View view){

        final Intent intent = new Intent(mContext, FormulariosContainerActivity.class);
        //Boton Hotel
        hotelButton = (Button)view.findViewById(R.id.hotel_button);
        hotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(APP_KEY, FRAGMENT_ID_HOTEL);
                startActivity(intent);
            }
        });
        //Boton Avion
        avionButton =(Button)view.findViewById(R.id.avion_button);
        avionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(APP_KEY,FRAGMENT_ID_AVION);
                startActivity(intent);
            }
        });
        //Boton Hotel Avion
        hotelAvionButton = (Button)view.findViewById(R.id.hotel_avion_button);
        hotelAvionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(APP_KEY,FRAGMENT_ID_HOTEL_AVION);
                startActivity(intent);
            }
        });

    }

}
