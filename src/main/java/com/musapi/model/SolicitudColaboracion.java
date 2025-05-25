/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class SolicitudColaboracion {
    
    @Id
    @Column(name = "idNotificacion")
    private Integer idNotificacion;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idNotificacion")
    private Notificacion notificacion;
    
    @Column(length = 15, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "idCancion", nullable = false)
    private Cancion cancion;

    //Getters y setters
    
    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    
    
}
