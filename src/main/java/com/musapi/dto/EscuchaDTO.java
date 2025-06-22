/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.time.LocalTime;

/**
 *
 * @author axell
 */
public class EscuchaDTO {
    private Integer idUsuario;
    private Integer idCancion;
    private Integer segundosEscucha;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public Integer getSegundosEscucha() {
        return segundosEscucha;
    }

    public void setSegundosEscucha(Integer segundosEscucha) {
        this.segundosEscucha = segundosEscucha;
    }

    
}
