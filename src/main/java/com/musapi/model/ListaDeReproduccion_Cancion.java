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
public class ListaDeReproduccion_Cancion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRelacion")
    private Integer idRelacion;
    
    @Column(name = "posicionCancion", nullable = false)
    private Integer posicionCancion;
    
    @ManyToOne
    @JoinColumn(name = "idListaDeReproduccion", nullable = false)
    private ListaDeReproduccion listaDeReproduccion;

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

    public Integer getPosicionCancion() {
        return posicionCancion;
    }

    public void setPosicionCancion(Integer posicionCancion) {
        this.posicionCancion = posicionCancion;
    }

    public ListaDeReproduccion getListaDeReproduccion() {
        return listaDeReproduccion;
    }

    public void setListaDeReproduccion(ListaDeReproduccion listaDeReproduccion) {
        this.listaDeReproduccion = listaDeReproduccion;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    
    
}
