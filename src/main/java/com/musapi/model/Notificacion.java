/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotificacion")
    private Integer idNotificacion;

    @Column(length = 300, nullable = false)
    private String mensaje;

    @Column(name = "fechaEnvio", nullable = false)
    private LocalDate fechaEnvio;

    @Column(name = "fueLeida", nullable = false)
    private Boolean fueLeida;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @OneToOne(mappedBy = "notificacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private SolicitudColaboracion solicitudColaboracion;

    //Getters y setters

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public Boolean getFueLeida() {
        return fueLeida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public SolicitudColaboracion getSolicitudColaboracion() {
        return solicitudColaboracion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setFueLeida(Boolean fueLeida) {
        this.fueLeida = fueLeida;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
