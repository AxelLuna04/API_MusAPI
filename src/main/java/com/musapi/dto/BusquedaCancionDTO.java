/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class BusquedaCancionDTO {
    private Integer idCancion;
    private String nombre;
    private String duracion;
    private String urlArchivo;
    private String urlFoto;
    private String nombreArtista;
    private String fechaPublicacion;
    private String nombreAlbum;
    private String categoriaMusical;

    public BusquedaCancionDTO(Integer idCancion, String nombre, String duracion, String urlArchivo, String urlFoto, String nombreArtista, String fechaPublicacion, String nombreAlbum, String categoriaMusical) {
        this.idCancion = idCancion;
        this.nombre = nombre;
        this.duracion = duracion;
        this.urlArchivo = urlArchivo;
        this.urlFoto = urlFoto;
        this.nombreArtista = nombreArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.nombreAlbum = nombreAlbum;
        this.categoriaMusical = categoriaMusical;
    }

    public BusquedaCancionDTO() {
    }

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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
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

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getCategoriaMusical() {
        return categoriaMusical;
    }

    public void setCategoriaMusical(String categoriaMusical) {
        this.categoriaMusical = categoriaMusical;
    }
    
    

}    