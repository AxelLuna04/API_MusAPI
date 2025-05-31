/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class CancionMasEscuchadaDTO {
    private String nombreCancion;
    private String nombreUsuarioArtista;
    private long minutosEscuchados;

    public CancionMasEscuchadaDTO(String nombreCancion, String nombreUsuarioArtista, long minutosEscuchados) {
        this.nombreCancion = nombreCancion;
        this.nombreUsuarioArtista = nombreUsuarioArtista;
        this.minutosEscuchados = minutosEscuchados;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreUsuarioArtista() {
        return nombreUsuarioArtista;
    }

    public void setNombreUsuarioArtista(String nombreUsuarioArtista) {
        this.nombreUsuarioArtista = nombreUsuarioArtista;
    }

    public long getMinutosEscuchados() {
        return minutosEscuchados;
    }

    public void setMinutosEscuchados(long minutosEscuchados) {
        this.minutosEscuchados = minutosEscuchados;
    }
    
    
}
