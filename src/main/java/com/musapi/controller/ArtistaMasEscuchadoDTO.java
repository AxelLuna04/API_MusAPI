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
    private long segundosEscuchados;

    public ArtistaMasEscuchadoDTO(String nombreArtista, long segundosEscuchados) {
        this.nombreArtista = nombreArtista;
        this.segundosEscuchados = segundosEscuchados;
    }
    
    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public long getSegundosEscuchados() {
        return segundosEscuchados;
    }

    public void setSegundosEscuchados(long segundosEscuchados) {
        this.segundosEscuchados = segundosEscuchados;
    }

    
    
}
