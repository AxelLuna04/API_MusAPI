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
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvaluacion")
    private Integer idEvaluacion;
    
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;
    
    @Column(length = 300, nullable = true)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idPerfilArtista", nullable = false)
    private PerfilArtista PerfilArtista;
    
    //Getters y setters

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PerfilArtista getPerfilArtista() {
        return PerfilArtista;
    }

    public void setPerfilArtista(PerfilArtista PerfilArtista) {
        this.PerfilArtista = PerfilArtista;
    }

    
}
