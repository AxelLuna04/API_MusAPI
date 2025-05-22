/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAlbum")
    private Integer idAlbum;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "fechaPublicacion", nullable = false)
    private LocalDate fechaPublicacion;

    @Lob
    @Column(name = "foto", nullable = false)
    private byte[] foto;

    @ManyToOne
    @JoinColumn(name = "idPerfilArtista", nullable = false)
    private PerfilArtista perfilArtista;
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones = new ArrayList<>();
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContenidoGuardado> contenidosGuardados = new ArrayList<>();
    
    //getters y setters

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public PerfilArtista getPerfilArtista() {
        return perfilArtista;
    }

    public void setPerfilArtista(PerfilArtista perfilArtista) {
        this.perfilArtista = perfilArtista;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<ContenidoGuardado> getContenidosGuardados() {
        return contenidosGuardados;
    }

    public void setContenidosGuardados(List<ContenidoGuardado> contenidosGuardados) {
        this.contenidosGuardados = contenidosGuardados;
    }

    
}
