/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.util.List;

/**
 *
 * @author axell
 */
public class ListaDeReproduccionDTO {
    private Integer idListaDeReproduccion;
    private String nombre;
    private String urlFoto;
    private String descripcion;
    private List<BusquedaCancionDTO> canciones;

    public ListaDeReproduccionDTO(Integer idListaDeReproduccion, String nombre, String urlFoto, String descripcion, List<BusquedaCancionDTO> canciones) {
        this.idListaDeReproduccion = idListaDeReproduccion;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
        this.canciones = canciones;
    }

    public ListaDeReproduccionDTO() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<BusquedaCancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<BusquedaCancionDTO> canciones) {
        this.canciones = canciones;
    }

    
    
}
