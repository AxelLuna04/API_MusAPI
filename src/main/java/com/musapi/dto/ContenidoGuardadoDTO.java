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
public class ContenidoGuardadoDTO {
    private Integer idUsuario;
    private Integer idContenidoGuardado;
    private TipoContenido tipoDeContenido;

    public ContenidoGuardadoDTO(Integer idUsuario, Integer idContenidoGuardado, TipoContenido tipoDeContenido) {
        this.idUsuario = idUsuario;
        this.idContenidoGuardado = idContenidoGuardado;
        this.tipoDeContenido = tipoDeContenido;
    }

    public ContenidoGuardadoDTO() {
    }
    
    

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdContenidoGuardado() {
        return idContenidoGuardado;
    }

    public void setIdContenidoGuardado(Integer idContenidoGuardado) {
        this.idContenidoGuardado = idContenidoGuardado;
    }

    public TipoContenido getTipoDeContenido() {
        return tipoDeContenido;
    }

    public void setTipoDeContenido(TipoContenido tipoDeContenido) {
        this.tipoDeContenido = tipoDeContenido;
    }
    
    
}
