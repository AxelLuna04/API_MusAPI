/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 * @param <T>
 */
public class RespuestaDTO<T> {
    private String mensaje;
    private T datos;

    public RespuestaDTO(String mensaje, T datos) {
        this.mensaje = mensaje;
        this.datos = datos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }
    
    
}
