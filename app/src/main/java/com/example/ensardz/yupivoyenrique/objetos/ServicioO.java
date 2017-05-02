package com.example.ensardz.yupivoyenrique.objetos;

/**
 * Created by ensardz on 25/04/2017.
 *
 *
 * Esta clase guarda los Servicios de hoteleria que se pidan a la API
 *
 * Un Servicio puede ser un Destino, Hotel+Avion, Vuelo de ida, Vuelo de salida, etcetera.
 */

public class ServicioO {
    String descripcion;
    public ServicioO(String descripcion){
        this.descripcion = descripcion;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
}
