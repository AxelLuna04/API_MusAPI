/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class Escucha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEscucha")
    private Integer idEscucha;
    
    @Column(name = "tiempoEscucha", nullable = true)
    private LocalTime tiempoEscucha;

    @Column(name = "fechaEscucha", nullable = false)
    private LocalDate fechaEscucha;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idCancion", nullable = false)
    private Cancion cancion;
    
    //getters y setters

    public Integer getIdEscucha() {
        return idEscucha;
    }

    public void setIdEscucha(Integer idEscucha) {
        this.idEscucha = idEscucha;
    }

    public LocalTime getTiempoEscucha() {
        return tiempoEscucha;
    }

    public void setTiempoEscucha(LocalTime tiempoEscucha) {
        this.tiempoEscucha = tiempoEscucha;
    }

    public LocalDate getFechaEscucha() {
        return fechaEscucha;
    }

    public void setFechaEscucha(LocalDate fechaEscucha) {
        this.fechaEscucha = fechaEscucha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    
    
}