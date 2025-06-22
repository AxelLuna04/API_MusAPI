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
public class EstadisticasPersonalesDTO {
    private List<String> topCanciones;
    private List<String> topArtistas;
    private long segundosEscuchados;

    public EstadisticasPersonalesDTO(List<String> topCanciones, List<String> topArtistas, long segundosEscuchados) {
        this.topCanciones = topCanciones;
        this.topArtistas = topArtistas;
        this.segundosEscuchados = segundosEscuchados;
    }

    public List<String> getTopCanciones() {
        return topCanciones;
    }

    public List<String> getTopArtistas() {
        return topArtistas;
    }

    public void setTopCanciones(List<String> topCanciones) {
        this.topCanciones = topCanciones;
    }

    public void setTopArtistas(List<String> topArtistas) {
        this.topArtistas = topArtistas;
    }

    public long getSegundosEscuchados() {
        return segundosEscuchados;
    }

    public void setSegundosEscuchados(long segundosEscuchados) {
        this.segundosEscuchados = segundosEscuchados;
    }
 
    
}

