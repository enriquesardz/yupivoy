package fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.BusquedaAutoCompleteAdapter;
import com.example.ensardz.yupivoyenrique.R;
import com.example.ensardz.yupivoyenrique.objetos.ServicioO;
import com.example.ensardz.yupivoyenrique.ui.DelayAutoCompleteTextView;
import com.example.ensardz.yupivoyenrique.utilidad.UtilidadFormularios;
import com.example.ensardz.yupivoyenrique.utilidad.FechaHospedaje;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_formulario_hotel_avion, container, false);

        fechaEntradaEditText = (EditText) view.findViewById(R.id.fecha_entrada_edittext);
        fechaSalidaEditText = (EditText) view.findViewById(R.id.fecha_salida_edittext);
        nochesTextView = (TextView)view.findViewById(R.id.noches_textview);

        //Crea los calendarios, este obj debe regresar la fecha 1, fecha 2, y las noches. 
        //TODO: Que el obj FechaHospedaje pueda regresar fecha1, fecha2, y las noches.
        FechaHospedaje fechaHospedaje = new FechaHospedaje(fechaEntradaEditText,fechaSalidaEditText,nochesTextView, mContext);

        //Se llenan todos los spinners
        Spinner presupuestoSpinner = (Spinner)view.findViewById(R.id.presupuesto_spinner);
        ArrayAdapter<CharSequence> presupuestoAdapter = ArrayAdapter.createFromResource(mContext,R.array.frmlr_hotel_avion_presupuesto_spinner_array, android.R.layout.simple_spinner_item);
        presupuestoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presupuestoSpinner.setAdapter(presupuestoAdapter);

        Spinner modoPagoSpinner = (Spinner)view.findViewById(R.id.modo_pago_spinner);
        ArrayAdapter<CharSequence> modoPAdapter = ArrayAdapter.createFromResource(mContext, R.array.frmlr_hotel_avion_modo_pago_spinner_array, android.R.layout.simple_spinner_item);
        modoPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modoPagoSpinner.setAdapter(modoPAdapter);

        Spinner tipoHotelSpinner = (Spinner)view.findViewById(R.id.tipo_hotel_spinner);
        ArrayAdapter<CharSequence> tipoHotelAdapter = ArrayAdapter.createFromResource(mContext,R.array.frmlr_hotel_avion_tipo_hotel_array, android.R.layout.simple_spinner_item);
        tipoHotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoHotelSpinner.setAdapter(tipoHotelAdapter);

        Spinner planHabitacionSpinner = (Spinner)view.findViewById(R.id.plan_habitacion_spinner);
        ArrayAdapter<CharSequence> planHabitacionAdapter = ArrayAdapter.createFromResource(mContext, R.array.frmlr_hotel_avion_plan_habitacion_array, android.R.layout.simple_spinner_item);
        planHabitacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planHabitacionSpinner.setAdapter(planHabitacionAdapter);

        Spinner tipoHabitacionSpinner = (Spinner)view.findViewById(R.id.tipo_habitacion_spinner);
        ArrayAdapter<CharSequence> tipoHabitacionAdapter = ArrayAdapter.createFromResource(mContext, R.array.frmlr_hotel_avion_tipo_habitacion_array, android.R.layout.simple_spinner_item);
        tipoHabitacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoHabitacionSpinner.setAdapter(tipoHabitacionAdapter);

        Spinner claseAvionSpinner = (Spinner)view.findViewById(R.id.clase_avion_spinner);
        ArrayAdapter<CharSequence> claseAvionAdapter = ArrayAdapter.createFromResource(mContext, R.array.frmlr_hotel_avion_clase_avion_array, android.R.layout.simple_spinner_item);
        claseAvionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        claseAvionSpinner.setAdapter(claseAvionAdapter);

        Spinner companiaAereaSpinner = (Spinner)view.findViewById(R.id.compania_aerea_spinner);
        ArrayAdapter<CharSequence> companiaAereaAdapter = ArrayAdapter.createFromResource(mContext,R.array.frmlr_hotel_avion_compañia_aerea_array, android.R.layout.simple_spinner_item);
        companiaAereaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companiaAereaSpinner.setAdapter(companiaAereaAdapter);

        //Crea los Delay Autocomplete text view para que traiga las sugerencias para los servicios.
        //Delay autocomplete Destino
        final DelayAutoCompleteTextView destinoAutoComplete = (DelayAutoCompleteTextView) view.findViewById(R.id.destino_datv);
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
        return view;
    }

    private void inicializarComponentes(){

    }

}
