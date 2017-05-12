package com.example.ensardz.yupivoyenrique;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ensardz.yupivoyenrique.objetos.ServicioO;
import com.example.ensardz.yupivoyenrique.utilidad.UtilidadFormularios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ensardz on 24/04/2017.
 *
 * Esta clase es un adaptador que debera de ser utilizado para los AutoCompleteTextView que
 * quieran mostrar sugerencias de Destinos, Hoteles/Destinos, Vuelos, etcetera.
 *
 * Ademas implementa Filterable para poder
 */

public class BusquedaAutoCompleteAdapter extends BaseAdapter implements Filterable{

    private static final String LOG = BusquedaAutoCompleteAdapter.class.getSimpleName();
    private static final String TAG_RESULTADOS = "results";
    private static final String TAG_LABEL = "Label";
    private static final String TAG_TIPO = "Type";
    private Context mContext;
    private String mTipoServicio;
    private List<ServicioO> listaResultadoServicios = new ArrayList<ServicioO>();

    //TODO: En el contstructor se debe pasar el tipo de busqueda que se realizara

    //EJ: Si es Hotel/Avion, Destino, Vuelo Salida/Entrada

    //Esto se hace para que est misma clase pueda llenar el AutoCompleteTextView independientemente de si
    //la peticion pida 1 servicio o 2 servicios.
    public BusquedaAutoCompleteAdapter(Context context, String tipoServicio){
        mContext = context;
        mTipoServicio = tipoServicio;
    }

    @Override
    public int getCount() {
        return listaResultadoServicios.size();
    }

    @Override
    public ServicioO getItem(int position) {
        return listaResultadoServicios.get(position);
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
        String tipoServicio = getItem(position).getTipoServicio();
        TextView listItem = (TextView)convertView.findViewById(R.id.busqueda_list_item_textview1);
        listItem.setText(getItem(position).getDescripcion());
        switch (tipoServicio){
            //TODO: AGREGAR VUELO SALIDA Y VUELO LLEGADA
            case UtilidadFormularios.ID_SERVICIO_DESTINO:
                listItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_icon_destino,0,0,0);
                break;
            case UtilidadFormularios.ID_SERVICIO_CIUDAD:
                listItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_icon_ciudad,0,0,0);
                break;
            case UtilidadFormularios.ID_SERVICIO_HOTEL:
                listItem.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list_icon_hotel,0,0,0);
                break;
            default:
                listItem.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0); //O cambiar por imagen default
                break;
        }
        return convertView;
    }

    //Este metodo le va a pasar a la clase BusquedaAsyncTask la palabra que se debe buscar
    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null){
                    List<ServicioO> servicios = encontrarResultados(constraint.toString());

                    filterResults.values = servicios;
                    filterResults.count = servicios.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0){
                    listaResultadoServicios = (List<ServicioO>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    //TODO: Revisar este metodo y terminarlo
    private List<ServicioO> encontrarResultados(String palabraBuscada){
        List<ServicioO> listaResultados;
        String JSONString = getJSONResponse(palabraBuscada);
        listaResultados = llenarListaResultados(JSONString);

        return listaResultados;
    }

    private String getJSONResponse(String palabraBuscada){

        String respuestaJSON;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("ajax.e-tsw.com")
                .appendPath("searchservices")
                .appendPath("getSearchJson.aspx")
                .appendQueryParameter("callback", "jQuery17208916521046776325_1429658910138")
                .appendQueryParameter("Lenguaje", "ESP")
                .appendQueryParameter("ItemTypes", mTipoServicio)
                .appendQueryParameter("ItemTypesOrder", ordenServicios(mTipoServicio))
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

    private String ordenServicios(String tipoServicio){
        String orden;
        switch (tipoServicio){
            case UtilidadFormularios.TIPO_SERVICIO_HOTEL:
                orden = UtilidadFormularios.ORDEN_SERVICIO_VACIO;
                break;
            case UtilidadFormularios.TIPO_SERVICIO_HOTEL_DESTINO_CIUDAD:
                orden = UtilidadFormularios.ORDEN_SERVICIO_HOTEL_DESTINO_CIUDAD;
                break;
            case UtilidadFormularios.TIPO_SERVICIO_VUELO_SALIDA:
                orden = UtilidadFormularios.ORDEN_SERVICIO_VACIO;
                break;
            case UtilidadFormularios.TIPO_SERVICIO_VUELO_LLEGADA:
                orden = UtilidadFormularios.ORDEN_SERVICIO_VACIO;
                break;
            default:
                orden = UtilidadFormularios.ORDEN_SERVICIO_VACIO;
                break;
        }
        return orden;
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

    private List<ServicioO> llenarListaResultados(String resultadoJSON){
        List<ServicioO> listaResultados = new ArrayList<ServicioO>();
        try {
            JSONObject objetoPrincipal = new JSONObject(resultadoJSON);
            JSONArray arrayResultados = objetoPrincipal.getJSONArray(TAG_RESULTADOS);
            for (int resultado=0; resultado < arrayResultados.length(); resultado++){
                JSONObject objetoResultado = arrayResultados.getJSONObject(resultado);
                String resultadoDescripcion = objetoResultado.getString(TAG_LABEL);
                String tipoServicio = objetoResultado.getString(TAG_TIPO);
                listaResultados.add(new ServicioO(resultadoDescripcion, tipoServicio));
            }
            return listaResultados;
        }
        catch(JSONException e){
            Log.e(LOG , e.toString());
        }
        return listaResultados;
    }
}



