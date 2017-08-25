package com.example.ensardz.yupivoyenrique.Utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by ensardz on 16/05/2017.
 */
//Esta clase recibe los edit text de las fechas de entrada y salida y el textview de noches para
//asegurar que el usuario eliga la fecha de entrada a partir de Hoy y no antes,
//que despues de elegir la fecha de entrada, solamente se pueda elegir las fechas posteriores
//como fecha de salida, despues se calculan las noches.

public class FechaHospedaje implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

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
            //El segundo date picker inicia 1 dia despues de la fecha  que se halla
            //escogido en el date picker de entrada
            datePickerDialogSalida.getDatePicker().setMinDate(calendarioEntrada.getTimeInMillis() + TimeUnit.DAYS.toMillis(1));
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