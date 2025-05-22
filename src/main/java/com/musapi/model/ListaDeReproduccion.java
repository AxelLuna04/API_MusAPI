/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class ListaDeReproduccion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "idListaDeReproduccion")
    private Integer idListaDeReproduccion;
        
    @Column (length = 100, nullable = false)
    private String nombre;
    
    @Column (length = 300, nullable = true)
    private String descripcion;
    
    @Lob
    @Column(name = "foto", nullable = true)
    private byte[] foto;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "listaDeReproduccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDeReproduccion_Cancion> listaDeReproduccion_CancionList = new ArrayList<>();
    
    @OneToMany(mappedBy = "listaDeReproduccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContenidoGuardado> contenidosGuardados = new ArrayList<>();
    
    //Getters y setters

    public Integer getIdListaDeReproduccion() {
        return idListaDeReproduccion;
    }

    public void setIdListaDeReproduccion(Integer idListaDeReproduccion) {
        this.idListaDeReproduccion = idListaDeReproduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ListaDeReproduccion_Cancion> getListaDeReproduccion_CancionList() {
        return listaDeReproduccion_CancionList;
    }

    public void setListaDeReproduccion_CancionList(List<ListaDeReproduccion_Cancion> listaDeReproduccion_CancionList) {
        this.listaDeReproduccion_CancionList = listaDeReproduccion_CancionList;
    }

    public List<ContenidoGuardado> getContenidosGuardados() {
        return contenidosGuardados;
    }

    public void setContenidosGuardados(List<ContenidoGuardado> contenidosGuardados) {
        this.contenidosGuardados = contenidosGuardados;
    }

    
}
