package layout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.BusquedaAutoCompleteAdapter;
import com.example.ensardz.yupivoyenrique.R;
import com.example.ensardz.yupivoyenrique.objetos.ServicioO;
import com.example.ensardz.yupivoyenrique.ui.DelayAutoCompleteTextView;

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

        //Crea el Delay Autocomplete text view para que traiga las sugerencias para los servicios.
        final DelayAutoCompleteTextView destinoAutoComplete = (DelayAutoCompleteTextView) view.findViewById(R.id.destino_datv);

        destinoAutoComplete.setThreshold(THRESHOLD);
        destinoAutoComplete.setAdapter(new BusquedaAutoCompleteAdapter(mContext));

        destinoAutoComplete.setLoadingIndicator((ProgressBar)view.findViewById(R.id.destino_indicador_carga));

//        destinoAutoComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                destinoAutoComplete.setText("");
//            }
//        });

        destinoAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServicioO servicio = (ServicioO) parent.getItemAtPosition(position);
                destinoAutoComplete.setText(servicio.getDescripcion());
                destinoAutoComplete.setSelected(false);
            }
        });

        return view;
    }


    //Esta clase recibe los edit text de las fechas de entrada y salida y el textview de noches para
    //asegurar que el usuario eliga la fecha de entrada a partir de Hoy y no antes,
    //que despues de elegir la fecha de entrada, solamente se pueda elegir las fechas posteriores
    //como fecha de salida, despues se calculan las noches.
    static class FechaHospedaje implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

        private EditText editTextEntrada, editTextSalida;
        private TextView nochesTextView;
        private Calendar calendarioEntrada, calendarioSalida;
        private Context context;
        private DatePicker DPEntrada;
        private DatePicker DPSalida;
        private long msDiferencia;
        private long nochesTotal;

        //TODO: Que la el calendario regrese a la fecha de hoy cuando se cambien las fechas

        /*
        No me gusta que la fecha inicial no regrese al dia actual... la segunda fecha tambien deberia
        * de regresar a la fecha inicial, en caso de que se elige una fecha muy en futuro y quiera regresar al dia
        * actual.
        * */

        public FechaHospedaje(EditText editTextEntrada, EditText editTextSalida, TextView nochesTextView, Context context){
            this.editTextEntrada = editTextEntrada;
            this.editTextSalida = editTextSalida;
            this.nochesTextView = nochesTextView;
            this.editTextEntrada.setOnClickListener(this);
            this.editTextSalida.setOnClickListener(this);
            this.context = context;
            calendarioEntrada = Calendar.getInstance();
            calendarioSalida = Calendar.getInstance();

        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String formatoFecha = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha, Locale.US);
            if(view.equals(DPEntrada)){
                calendarioEntrada.set(Calendar.YEAR, year);
                calendarioEntrada.set(Calendar.MONTH, month);
                calendarioEntrada.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editTextEntrada.setText(sdf.format(calendarioEntrada.getTime()));
                editTextSalida.setEnabled(true);
            }
            else if(view.equals(DPSalida)){
                calendarioSalida.set(Calendar.YEAR, year);
                calendarioSalida.set(Calendar.MONTH, month);
                calendarioSalida.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                editTextSalida.setText(sdf.format(calendarioSalida.getTime()));
                setNoches();
                nochesTextView.setText(getNoches());
            }
        }

        @Override
        public void onClick(View v) {

            if(v.equals(editTextEntrada)) {
                resetearValores();
                DatePickerDialog datePickerDialogEntrada = new DatePickerDialog(context, this,
                        calendarioEntrada.get(Calendar.YEAR),
                        calendarioEntrada.get(Calendar.MONTH),
                        calendarioEntrada.get(Calendar.DAY_OF_MONTH));
                datePickerDialogEntrada.getDatePicker().setMinDate(System.currentTimeMillis());
                DPEntrada = datePickerDialogEntrada.getDatePicker();
                datePickerDialogEntrada.show();
            }
            else if (v.equals(editTextSalida)){
                DatePickerDialog datePickerDialogSalida = new DatePickerDialog(context, this,
                        calendarioSalida.get(Calendar.YEAR),
                        calendarioSalida.get(Calendar.MONTH),
                        calendarioSalida.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialogSalida.getDatePicker().setMinDate(calendarioEntrada.getTimeInMillis());
                DPSalida = datePickerDialogSalida.getDatePicker();
                datePickerDialogSalida.show();
            }
        }

        private void setNoches(){
            msDiferencia = calendarioSalida.getTimeInMillis() - calendarioEntrada.getTimeInMillis();
            nochesTotal = TimeUnit.MILLISECONDS.toDays(msDiferencia);
        }

        public String getNoches(){
            return String.valueOf(nochesTotal);
        }

        private void resetearValores(){
            nochesTextView.setText("0");
            editTextSalida.setText("");
            editTextSalida.setEnabled(false);
        }

    }
}
