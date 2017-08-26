package com.example.ensardz.yupivoyenrique.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.UI.BusquedaAutoCompleteAdapter;
import com.example.ensardz.yupivoyenrique.R;
import com.example.ensardz.yupivoyenrique.Data.POJOs.ServicioO;
import com.example.ensardz.yupivoyenrique.UI.DelayAutoCompleteTextView;
import com.example.ensardz.yupivoyenrique.Utility.FechaHospedaje;
import com.example.ensardz.yupivoyenrique.Utility.UtilidadFormularios;


public class FormularioAvion extends Fragment {

    private static final String LOG = FormularioAvion.class.getSimpleName();
    private static final int THRESHOLD = 3;

    private RadioGroup tipoVueloRG;
    private Spinner modoPagoSpinner;
    private DelayAutoCompleteTextView aeropuertoSalidaDACTV;
    private DelayAutoCompleteTextView destinoDACTV;
    private EditText fechaEntradaEditText;
    private EditText fechaSalidaEditText;
    private TextView nochesTextView;

    private Context mContext;

    public FormularioAvion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_avion, container, false);
        mContext = getContext();
        inicializarComponentes(view);
        return view;
    }


    private void inicializarComponentes(View view){
        tipoVueloRG = (RadioGroup)view.findViewById(R.id.tipo_vuelo_radio_group);
        modoPagoSpinner = (Spinner)view.findViewById(R.id.modo_pago_spinner);

        //Calendario
        fechaEntradaEditText = (EditText)view.findViewById(R.id.fecha_entrada_edittext);
        fechaSalidaEditText = (EditText)view.findViewById(R.id.fecha_salida_edittext);
        nochesTextView = (TextView)view.findViewById(R.id.noches_textview);
        FechaHospedaje fechaHospedaje = new FechaHospedaje(fechaEntradaEditText,fechaSalidaEditText, nochesTextView, mContext);

        //Aeropuerto Salida Auto Complete
        aeropuertoSalidaDACTV = (DelayAutoCompleteTextView)view.findViewById(R.id.aeropuerto_salida_dactv);
        aeropuertoSalidaDACTV.setThreshold(THRESHOLD);
        aeropuertoSalidaDACTV.setAdapter(new BusquedaAutoCompleteAdapter(mContext, UtilidadFormularios.TIPO_SERVICIO_VUELO_SALIDA));
        aeropuertoSalidaDACTV.setLoadingIndicator((ProgressBar)view.findViewById(R.id.aeropuerto_salida_indicador_carga));
        aeropuertoSalidaDACTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aeropuertoSalidaDACTV.setText("");
            }
        });
        aeropuertoSalidaDACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicioO servicio = (ServicioO) parent.getItemAtPosition(position);
                aeropuertoSalidaDACTV.setText(servicio.getDescripcion());
            }
        });

        //Destino autocomplete
        destinoDACTV = (DelayAutoCompleteTextView)view.findViewById(R.id.destino_dactv);
        destinoDACTV.setThreshold(THRESHOLD);
        destinoDACTV.setAdapter(new BusquedaAutoCompleteAdapter(mContext, UtilidadFormularios.TIPO_SERVICIO_DESTINO));
        destinoDACTV.setLoadingIndicator((ProgressBar)view.findViewById(R.id.destino_indicador_carga));
        destinoDACTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinoDACTV.setText("");
            }
        });
        destinoDACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicioO servicio = (ServicioO) parent.getItemAtPosition(position);
                destinoDACTV.setText(servicio.getDescripcion());
            }
        });
    }
}
