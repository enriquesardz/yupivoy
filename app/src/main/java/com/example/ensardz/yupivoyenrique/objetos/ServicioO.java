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
    String tipoServicio;
    public ServicioO(String descripcion, String tipoServicio){
        this.descripcion = descripcion;
        this.tipoServicio = tipoServicio;
    }
    public String getDescripcion(){
        return this.descripcion;
    }
    public String getTipoServicio(){return this.tipoServicio;}
}
