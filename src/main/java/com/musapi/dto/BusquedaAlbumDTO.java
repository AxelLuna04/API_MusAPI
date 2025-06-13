/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.util.List;

/**
 *
 * @author axell
 */
public class BusquedaAlbumDTO {
    private Integer idAlbum;
    private String nombreAlbum;
    private String nombreArtista;
    private String fechaPublicacion;
    private String urlFoto;
    private List<BusquedaCancionDTO> canciones;

    public BusquedaAlbumDTO() {
    }

    public BusquedaAlbumDTO(Integer idAlbum, String nombreAlbum, String nombreArtista, String fechaPublicacion, String urlFoto, List<BusquedaCancionDTO> canciones) {
        this.idAlbum = idAlbum;
        this.nombreAlbum = nombreAlbum;
        this.nombreArtista = nombreArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.urlFoto = urlFoto;
        this.canciones = canciones;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }
    
    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
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

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public List<BusquedaCancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<BusquedaCancionDTO> canciones) {
        this.canciones = canciones;
    }

    
}
