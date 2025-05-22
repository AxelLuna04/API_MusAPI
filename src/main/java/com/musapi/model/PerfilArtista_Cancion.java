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
public class PerfilArtista_Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRelacion")
    private Integer idRelacion;

    @ManyToOne
    @JoinColumn(name = "idPerfilArtista", nullable = false)
    private PerfilArtista perfilArtista;

    @ManyToOne
    @JoinColumn(name = "idCancion", nullable = false)
    private Cancion cancion;

    //Getters y setters

    public Integer getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(Integer idRelacion) {
        this.idRelacion = idRelacion;
    }

    public PerfilArtista getPerfilArtista() {
        return perfilArtista;
    }

    public void setPerfilArtista(PerfilArtista perfilArtista) {
        this.perfilArtista = perfilArtista;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    
    
}

