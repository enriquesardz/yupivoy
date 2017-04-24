package com.example.ensardz.yupivoyenrique;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ensardz on 24/04/2017.
 */

public class BusquedaAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private static final int MAX_RESULTADOS = 10;
    private Context mContext;
    private List<BusquedaO> listaResultados = new ArrayList<BusquedaO>();

    //TODO: En el contstructor se debe pasar el tipo de busqueda que se realizara
    //EJ: Si es Hotel/Avion, Destino, Vuelo Salida/Entrada
    public BusquedaAutoCompleteAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return listaResultados.size();
    }

    @Override
    public BusquedaO getItem(int position) {
        return listaResultados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.busqueda_fila_listitem, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.busqueda_list_item_textview1)).setText(getItem(position).getDescripcion());
        return convertView;
    }

    //Este metodo le va a pasar a la clase BusquedaAsyncTask la palabra que se debe buscar
    @Override
    public Filter getFilter() {
        return null;
    }

    class BusquedaAsyncTask extends AsyncTask<Void, Void, String>{

        private String respuestaJSON;

        //Este metodo manda una peticion al web service
        //se crea un string con la respusta, el JSON string se pasa/guarda para
        //manipularlo despues.

        @Override
        protected String doInBackground(Void... params) {
            //URL Prueba:
            // http://ajax.e-tsw.com/searchservices/getSearchJson.aspx?callback=jQuery17208916521046776325_1429658910138&Lenguaje=ESP&ItemTypes=H:5,D:5&ItemTypesOrder=D&Filters=&PalabraBuscada=can&Af=undefined
            //TODO: Terminar el URI builder
            String urlPrueba = "http://ajax.e-tsw.com/searchservices/getSearchJson.aspx?callback=jQuery17208916521046776325_1429658910138&Lenguaje=ESP&ItemTypes=H:5,D:5&ItemTypesOrder=D&Filters=&PalabraBuscada=can&Af=undefined";
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("www.ajax.e-tsw.com")
                    .appendPath("searchservies")
                    .appendPath("getSearchJson.aspx")
                    .appendQueryParameter("callback", "jQuery17208916521046776325_1429658910138")
                    .appendQueryParameter("Lenguaje", "ESP")
                    .appendQueryParameter("ItemTypes", "D:5")
                    .appendQueryParameter("ItemTypesOrder", "D")
                    .appendQueryParameter("Filters", "")
                    .appendQueryParameter("PalabraBuscada", "can")
                    .appendQueryParameter("Af", "undefined");

            try{
                URL url = new URL(builder.build().toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                String line = "";
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }

                respuestaJSON = sb.toString();

                isr.close();
                reader.close();

            }
            catch(IOException e){
                Log.e("Error: " ,e.toString());
            }
            Log.e("RESPUSTA: ", respuestaJSON);
            return null;
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
        }
    }
}

class BusquedaO{
    String descripcion;
    public BusquedaO(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }
}
