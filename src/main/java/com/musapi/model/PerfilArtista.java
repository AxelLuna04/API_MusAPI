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
public class PerfilArtista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerfilArtista")
    private Integer idPerfilArtista;
    
    @Column(length = 300, nullable = true, unique = true)
    private String descripcion;

    @Column(name = "urlFoto", length = 300)
    private String urlFoto;

    @OneToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "perfilArtista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluacion> evaluaciones = new ArrayList<>();
    
    @OneToMany(mappedBy = "perfilArtista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albumes = new ArrayList<>();
    
    @OneToMany(mappedBy = "perfilArtista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerfilArtista_Cancion> perfilArtista_CancionList = new ArrayList<>();
    
    @OneToMany(mappedBy = "perfilArtista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContenidoGuardado> contenidosGuardados = new ArrayList<>();
    
    //Getters y setters

    public Integer getIdPerfilArtista() {
        return idPerfilArtista;
    }

    public void setIdPerfilArtista(Integer idPerfilArtista) {
        this.idPerfilArtista = idPerfilArtista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(List<Album> albumes) {
        this.albumes = albumes;
    }

    public List<PerfilArtista_Cancion> getPerfilArtista_CancionList() {
        return perfilArtista_CancionList;
    }

    public void setPerfilArtista_CancionList(List<PerfilArtista_Cancion> perfilArtista_CancionList) {
        this.perfilArtista_CancionList = perfilArtista_CancionList;
    }

    public List<ContenidoGuardado> getContenidosGuardados() {
        return contenidosGuardados;
    }

    public void setContenidosGuardados(List<ContenidoGuardado> contenidosGuardados) {
        this.contenidosGuardados = contenidosGuardados;
    }

    
}
