/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class Cancion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCancion")
    private Integer idCancion;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "urlArchivo", length = 100)
    private String urlArchivo;

    @Column(name = "duracion", nullable = false)
    private LocalTime duracion;

    @Column(name = "fechaPublicacion", nullable = true)
    private LocalDate fechaPublicacion;

    @Column(name = "estado", length = 15, nullable = false)
    private String estado;
    
    @Column(name = "urlFoto", length = 300, nullable = true) 
    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "idCategoriaMusical", nullable = false)
    private CategoriaMusical categoriaMusical;
    
    @ManyToOne
    @JoinColumn(name = "idAlbum", nullable = true)
    private Album album;

    @Column(name = "posicionEnAlbum", nullable = true)
    private Integer posicionEnAlbum;
    
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerfilArtista_Cancion> perfilArtista_CancionList = new ArrayList<>();
    
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDeReproduccion_Cancion> listaDeReproduccion_CancionList = new ArrayList<>();
    
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Escucha> escuchas = new ArrayList<>();
    
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContenidoGuardado> contenidosGuardados = new ArrayList<>();
    
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudColaboracion> solicitudesColaboracion = new ArrayList<>();
    
    //Getters y setters

    public Integer getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Integer idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public CategoriaMusical getCategoriaMusical() {
        return categoriaMusical;
    }

    public void setCategoriaMusical(CategoriaMusical categoriaMusical) {
        this.categoriaMusical = categoriaMusical;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Integer getPosicionEnAlbum() {
        return posicionEnAlbum;
    }

    public void setPosicionEnAlbum(Integer posicionEnAlbum) {
        this.posicionEnAlbum = posicionEnAlbum;
    }

    public List<PerfilArtista_Cancion> getPerfilArtista_CancionList() {
        return perfilArtista_CancionList;
    }

    public void setPerfilArtista_CancionList(List<PerfilArtista_Cancion> perfilArtista_CancionList) {
        this.perfilArtista_CancionList = perfilArtista_CancionList;
    }

    public List<ListaDeReproduccion_Cancion> getListaDeReproduccion_CancionList() {
        return listaDeReproduccion_CancionList;
    }

    public void setListaDeReproduccion_CancionList(List<ListaDeReproduccion_Cancion> listaDeReproduccion_CancionList) {
        this.listaDeReproduccion_CancionList = listaDeReproduccion_CancionList;
    }

    public List<Escucha> getEscuchas() {
        return escuchas;
    }

    public void setEscuchas(List<Escucha> escuchas) {
        this.escuchas = escuchas;
    }

    public List<ContenidoGuardado> getContenidosGuardados() {
        return contenidosGuardados;
    }

    public void setContenidosGuardados(List<ContenidoGuardado> contenidosGuardados) {
        this.contenidosGuardados = contenidosGuardados;
    }

    public List<SolicitudColaboracion> getSolicitudesColaboracion() {
        return solicitudesColaboracion;
    }

    public void setSolicitudesColaboracion(List<SolicitudColaboracion> solicitudesColaboracion) {
        this.solicitudesColaboracion = solicitudesColaboracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
    
    
    
}