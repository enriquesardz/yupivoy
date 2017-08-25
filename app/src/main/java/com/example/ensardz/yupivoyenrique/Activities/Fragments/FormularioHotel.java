package com.example.ensardz.yupivoyenrique.Activities.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ensardz.yupivoyenrique.FormulariosContainer;
import com.example.ensardz.yupivoyenrique.R;


public class FormularioHotel extends Fragment {


    private static final String LOG = FormularioHotel.class.getSimpleName();
    private static final int THRESHOLD = 3;
    private static final String TITULO_FORMULARIO = "Hotel";

//    private EditText fechaEntradaEditText;
//    private EditText fechaSalidaEditText;
//    private TextView nochesTextView;

    private Context mContext;

    public FormularioHotel() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity formContainerActivity = getActivity();
        if(formContainerActivity instanceof FormulariosContainer){
            ((FormulariosContainer)getActivity()).getSupportActionBar().setTitle(TITULO_FORMULARIO);
        }
        
        View view = inflater.inflate(R.layout.fragment_formulario_hotel, container, false);
        mContext = getContext();


        return view;
    }



}
