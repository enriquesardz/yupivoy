package com.example.ensardz.yupivoyenrique.Utility;

/**
 * Created by ensardz on 09/05/2017.
 */

public class UtilidadFormularios {
    //Numero de resultados que regresa el request al web service
    public static final String MAX_RESULTADO_SERVICIOS = "5";

    //El "ID" que se utiliza en el web service para identificar el tipo de servicio que es
    //Estos identificadores se utilizan especificamente para identificar el servicio y utilizar la imagen adecuada.
    public static final String ID_SERVICIO_HOTEL = "H";
    public static final String ID_SERVICIO_DESTINO = "D";
    public static final String ID_SERVICIO_CIUDAD = "C";
    public static final String ID_SERVICIO_VUELO_LLEGADA= "A";
    public static final String ID_SERVICIO_VUELO_SALIDA = "P";

    //Tipo de servicio que se le va a pedir al web service
    public static final String TIPO_SERVICIO_HOTEL = "H:" + MAX_RESULTADO_SERVICIOS; //EJ= H:10
    public static final String TIPO_SERVICIO_DESTINO = "D:" + MAX_RESULTADO_SERVICIOS;
    public static final String TIPO_SERVICIO_CIUDAD = "C:" + MAX_RESULTADO_SERVICIOS;
    public static final String TIPO_SERVICIO_HOTEL_DESTINO = "H:" + MAX_RESULTADO_SERVICIOS + ",D:" + MAX_RESULTADO_SERVICIOS; //H:10,D:10
    public static final String TIPO_SERVICIO_HOTEL_DESTINO_CIUDAD = "H:"+MAX_RESULTADO_SERVICIOS+",D:"+MAX_RESULTADO_SERVICIOS+",C:"+MAX_RESULTADO_SERVICIOS; //H:10,D:10,C:10
    public static final String TIPO_SERVICIO_VUELO_LLEGADA = "A:"+MAX_RESULTADO_SERVICIOS; //A:10
    public static final String TIPO_SERVICIO_VUELO_SALIDA = "P:"+MAX_RESULTADO_SERVICIOS; //P:10

    //Orden de como debe regresar los servicios el web service
    //Hotel, Vuelo de llegada , Vuelo de salida y cualquier otro que solo sea 1 servicio,
    //no requieren un orden por lo que se puede tomar el valor de ORDEN_SERVICIO_VACIO
    public static final String ORDEN_SERVICIO_VACIO = "";
    public static final String ORDEN_SERVICIO_DESTINO = "D";
    public static final String ORDEN_SERVICIO_CIUDAD = "C";
    public static final String ORDEN_SERVICIO_HOTEL_DESTINO = "H,D";
    public static final String ORDEN_SERVICIO_HOTEL_DESTINO_CIUDAD = "D,C,H";

}
