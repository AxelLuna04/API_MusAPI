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
    private long minutosEscuchados;

    public EstadisticasPersonalesDTO(List<String> topCanciones, List<String> topArtistas, long minutosEscuchados) {
        this.topCanciones = topCanciones;
        this.topArtistas = topArtistas;
        this.minutosEscuchados = minutosEscuchados;
    }

    public List<String> getTopCanciones() {
        return topCanciones;
    }

    public List<String> getTopArtistas() {
        return topArtistas;
    }

    public long getMinutosEscuchados() {
        return minutosEscuchados;
    }

    public void setTopCanciones(List<String> topCanciones) {
        this.topCanciones = topCanciones;
    }

    public void setTopArtistas(List<String> topArtistas) {
        this.topArtistas = topArtistas;
    }

    public void setMinutosEscuchados(long minutosEscuchados) {
        this.minutosEscuchados = minutosEscuchados;
    }
    
    
}

