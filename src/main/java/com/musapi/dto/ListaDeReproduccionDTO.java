/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class ListaDeReproduccionDTO {
    private Integer idListaDeReproduccion;
    private String nombre;
    private String urlFoto;

    public ListaDeReproduccionDTO() {
    }

    
    
    public ListaDeReproduccionDTO(Integer idListaDeReproduccion, String nombre, String urlFoto) {
        this.idListaDeReproduccion = idListaDeReproduccion;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
    }

    public Integer getIdListaDeReproduccion() {
        return idListaDeReproduccion;
    }

    public void setIdListaDeReproduccion(Integer idListaDeReproduccion) {
        this.idListaDeReproduccion = idListaDeReproduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    
}
