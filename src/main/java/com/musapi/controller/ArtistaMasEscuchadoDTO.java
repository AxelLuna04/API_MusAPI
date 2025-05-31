/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

/**
 *
 * @author axell
 */
public class ArtistaMasEscuchadoDTO {
    private String nombreArtista;
    private long minutosEscuchados;

    public ArtistaMasEscuchadoDTO(String nombreArtista, long minutosEscuchados) {
        this.nombreArtista = nombreArtista;
        this.minutosEscuchados = minutosEscuchados;
    }
    
    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public long getMinutosEscuchados() {
        return minutosEscuchados;
    }

    public void setMinutosEscuchados(long minutosEscuchados) {
        this.minutosEscuchados = minutosEscuchados;
    }
    
    
}
