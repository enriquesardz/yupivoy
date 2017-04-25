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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.example.ensardz.yupivoyenrique.objetos.ServicioO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ensardz on 24/04/2017.
 */

public class BusquedaAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private static final String LOG = BusquedaAutoCompleteAdapter.class.getSimpleName();
    private static final int MAX_RESULTADOS = 10;
    private Context mContext;
    private List<ServicioO> listaServicios = new ArrayList<ServicioO>();

    //TODO: En el contstructor se debe pasar el tipo de busqueda que se realizara
    //EJ: Si es Hotel/Avion, Destino, Vuelo Salida/Entrada
    public BusquedaAutoCompleteAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return listaServicios.size();
    }

    @Override
    public ServicioO getItem(int position) {
        return listaServicios.get(position);
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
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null){

                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        }
        return filter;
    }

    //TODO: Revisar este metodo y terminarlo
    private List<ServicioO> encontrarResultados(String palabraBuscada){
        BusquedaAsyncTask busquedaAsyncTask = new BusquedaAsyncTask();
        busquedaAsyncTask.execute(palabraBuscada);
        return busquedaAsyncTask.getListaResultados();
    }

    //AsyncTask para crear el List con los resultados
    class BusquedaAsyncTask extends AsyncTask<String, Void, String>{

        private static final String TAG_RESULTADOS = "results";
        private static final String TAG_LABEL = "Label";
        private String respuestaJSON;
        private List<ServicioO> listaResultados = new ArrayList<ServicioO>();

        //Este metodo manda una peticion al web service
        //se crea un string con la respuesta, el JSON string se pasa/guarda para
        //manipularlo despues.

        @Override
        protected String doInBackground(String... params) {

            String palabraBuscada = params[0];
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("ajax.e-tsw.com")
                    .appendPath("searchservices")
                    .appendPath("getSearchJson.aspx")
                    .appendQueryParameter("callback", "jQuery17208916521046776325_1429658910138")
                    .appendQueryParameter("Lenguaje", "ESP")
                    .appendQueryParameter("ItemTypes", "D:5")
                    .appendQueryParameter("ItemTypesOrder", "D")
                    .appendQueryParameter("Filters", "")
                    .appendQueryParameter("PalabraBuscada", palabraBuscada)
                    .appendQueryParameter("Af", "undefined");

            InputStreamReader isr = null;
            BufferedReader reader = null;
            try{
                URL url = new URL(builder.build().toString());
                Log.d(LOG + " Conexion a URL: ", url.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                String line = "";
                isr = new InputStreamReader(connection.getInputStream());
                reader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null){
                    sb.append(line + "\n");
                }

                respuestaJSON = jsonCallbackToJson(sb.toString());
            }
            catch(IOException e){
                Log.e(LOG + " Error: " ,e.toString());
                return null;
            }
            finally {
                try{
                    if(isr != null){
                        isr.close();
                    }
                    if (reader != null){
                        reader.close();
                    }
                }
                catch (IOException e){

                }
            }
            Log.e(LOG+" RESPUSTA: ", respuestaJSON);

            return respuestaJSON;
        }

        //Convierte el jsonCallback a un Json string que sea manipulable
        public String jsonCallbackToJson(String jsonCallback){
            String jsonString;
            String nombreFuncion = null;
            StringTokenizer st = new StringTokenizer(jsonCallback, "()");
            while(st.hasMoreElements()){
                nombreFuncion = st.nextToken();
                break;
            }
            Log.e(LOG,nombreFuncion);
            jsonString = jsonCallback.substring(nombreFuncion.length()+1, jsonCallback.length()-2);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String resultadoJSON) {
            super.onPostExecute(resultadoJSON);
            if(resultadoJSON == null){
                Log.e(LOG +" Metodo onPostExecute: ", "Parametro null");
            }

            llenarListaResultados(resultadoJSON);
        }

        private void llenarListaResultados(String resultadoJSON){
            try {
                JSONObject objetoPrincipal = new JSONObject(resultadoJSON);
                JSONArray arrayResultados = objetoPrincipal.getJSONArray(TAG_RESULTADOS);
                for (int resultado=0; resultado < arrayResultados.length(); resultado++){
                    JSONObject objetoResultado = arrayResultados.getJSONObject(resultado);
                    String resultadoDescripcion = objetoResultado.getString(TAG_LABEL);
                    listaResultados.add(new ServicioO(resultadoDescripcion));
                }
            }
            catch(JSONException e){
                Log.e(LOG , e.toString());
            }
        }

        public List<ServicioO> getListaResultados(){
            return this.listaResultados;
        }
    }
}



