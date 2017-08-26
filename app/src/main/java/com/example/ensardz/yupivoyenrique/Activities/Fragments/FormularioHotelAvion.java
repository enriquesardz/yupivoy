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
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.UI.BusquedaAutoCompleteAdapter;
import com.example.ensardz.yupivoyenrique.R;
import com.example.ensardz.yupivoyenrique.Data.POJOs.ServicioO;
import com.example.ensardz.yupivoyenrique.UI.DelayAutoCompleteTextView;
import com.example.ensardz.yupivoyenrique.Utility.UtilidadFormularios;
import com.example.ensardz.yupivoyenrique.Utility.FechaHospedaje;


public class FormularioHotelAvion extends Fragment {

    private static final String LOG = FormularioHotelAvion.class.getSimpleName();
    private static final int THRESHOLD = 3;

    private EditText fechaEntradaEditText;
    private EditText fechaSalidaEditText;
    private TextView nochesTextView;

    private Context mContext;


    public FormularioHotelAvion() {
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
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_formulario_hotel_avion, container, false);

        inicializarComponentes(view);

        return view;
    }

    private void inicializarComponentes(View view){
        fechaEntradaEditText = (EditText) view.findViewById(R.id.fecha_entrada_edittext);
        fechaSalidaEditText = (EditText) view.findViewById(R.id.fecha_salida_edittext);
        nochesTextView = (TextView)view.findViewById(R.id.noches_textview);

        //Crea los calendarios, este obj debe regresar la fecha 1, fecha 2, y las noches.
        //TODO: Que el obj FechaHospedaje pueda regresar fecha1, fecha2, y las noches.
        //TODO: Las noches se deben de actualizar cuando CAMBIA el contenido del text view de fecha 2.
        //TODO: Que el textview 2 se habilite cuando cambie el contenido del textview 1.
        FechaHospedaje fechaHospedaje = new FechaHospedaje(fechaEntradaEditText,fechaSalidaEditText,nochesTextView, mContext);


        //Crea los Delay Autocomplete text view para que traiga las sugerencias para los servicios.
        //Delay autocomplete Destino
        final DelayAutoCompleteTextView destinoAutoComplete = (DelayAutoCompleteTextView) view.findViewById(R.id.destino_dactv);
        destinoAutoComplete.setThreshold(THRESHOLD);
        destinoAutoComplete.setAdapter(new BusquedaAutoCompleteAdapter(mContext, UtilidadFormularios.TIPO_SERVICIO_HOTEL_DESTINO_CIUDAD));
        destinoAutoComplete.setLoadingIndicator((ProgressBar)view.findViewById(R.id.destino_indicador_carga));
        destinoAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinoAutoComplete.setText("");
            }
        });
        destinoAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicioO servicio = (ServicioO) parent.getItemAtPosition(position);
                destinoAutoComplete.setText(servicio.getDescripcion());
            }
        });
        //Delay autocomplete Hotel
        final DelayAutoCompleteTextView hotelAutoComplete = (DelayAutoCompleteTextView) view.findViewById(R.id.hotel_datv);
        hotelAutoComplete.setThreshold(THRESHOLD);
        hotelAutoComplete.setAdapter(new BusquedaAutoCompleteAdapter(mContext, UtilidadFormularios.TIPO_SERVICIO_HOTEL));
        hotelAutoComplete.setLoadingIndicator((ProgressBar)view.findViewById(R.id.hotel_indicador_carga));
        hotelAutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotelAutoComplete.setText("");
            }
        });
        hotelAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicioO servicioO = (ServicioO) parent.getItemAtPosition(position);
                hotelAutoComplete.setText(servicioO.getDescripcion());
            }
        });
    }

}
