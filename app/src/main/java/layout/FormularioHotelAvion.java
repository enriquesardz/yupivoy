package layout;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.BusquedaAutoCompleteAdapter;
import com.example.ensardz.yupivoyenrique.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FormularioHotelAvion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FormularioHotelAvion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormularioHotelAvion extends Fragment {

    private static final String LOG = FormularioHotelAvion.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    EditText fechaEntradaEditText;
    EditText fechaSalidaEditText;
    TextView nochesTextView;

    public FormularioHotelAvion() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormularioHotelAvion.
     */
    // TODO: Rename and change types and number of parameters
    public static FormularioHotelAvion newInstance(String param1, String param2) {
        FormularioHotelAvion fragment = new FormularioHotelAvion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_formulario_hotel_avion, container, false);
        fechaEntradaEditText = (EditText) view.findViewById(R.id.fecha_entrada_edittext);
        fechaSalidaEditText = (EditText) view.findViewById(R.id.fecha_salida_edittext);
        nochesTextView = (TextView)view.findViewById(R.id.noches_textview);
        new FechaHospedaje(fechaEntradaEditText,fechaSalidaEditText,nochesTextView,  mContext);

        return view;

    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

        /*TODO: No me gusta que la fecha inicial no regrese al dia actual... la segunda fecha tambien deberia
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
