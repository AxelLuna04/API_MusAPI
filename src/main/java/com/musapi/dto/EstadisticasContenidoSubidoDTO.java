/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class EstadisticasContenidoSubidoDTO {
    private int numeroOyentes;
    private int numeroGuardados;

    public EstadisticasContenidoSubidoDTO(int numeroOyentes, int numeroGuardados) {
        this.numeroOyentes = numeroOyentes;
        this.numeroGuardados = numeroGuardados;
    }
    

    public int getNumeroOyentes() {
        return numeroOyentes;
    }

    public int getNumeroGuardados() {
        return numeroGuardados;
    }

    public void setNumeroOyentes(int numeroOyentes) {
        this.numeroOyentes = numeroOyentes;
    }

    public void setNumeroGuardados(int numeroGuardados) {
        this.numeroGuardados = numeroGuardados;
    }
    
    
}
