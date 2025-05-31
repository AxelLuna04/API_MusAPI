/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import com.musapi.enums.TipoContenido;

/**
 *
 * @author axell
 */
public class ContenidoDTO {
    private Integer idContenido;
    private TipoContenido tipoContendio;
    private String motivoEliminacion;

    public Integer getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Integer idContenido) {
        this.idContenido = idContenido;
    }

    public TipoContenido getTipoContendio() {
        return tipoContendio;
    }

    public void setTipoContendio(TipoContenido tipoContendio) {
        this.tipoContendio = tipoContendio;
    }

    public String getMotivoEliminacion() {
        return motivoEliminacion;
    }

    public void setMotivoEliminacion(String motivoEliminacion) {
        this.motivoEliminacion = motivoEliminacion;
    }
    
    
}
