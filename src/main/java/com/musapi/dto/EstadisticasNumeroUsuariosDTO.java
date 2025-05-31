/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class EstadisticasNumeroUsuariosDTO {
    private Integer totalUsuarios;
    private Integer totalArtistas;

    public EstadisticasNumeroUsuariosDTO(Integer totalUsuarios, Integer totalArtistas) {
        this.totalUsuarios = totalUsuarios;
        this.totalArtistas = totalArtistas;
    }

    
    public Integer getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(Integer totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public Integer getTotalArtistas() {
        return totalArtistas;
    }

    public void setTotalArtistas(Integer totalArtistas) {
        this.totalArtistas = totalArtistas;
    }
    
    
}
