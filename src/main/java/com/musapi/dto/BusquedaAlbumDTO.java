/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author axell
 */
public class BusquedaAlbumDTO {
    private String nombreAlbum;
    private String nombreArtista;
    private LocalDate fechaPublicacion;
    private String urlFoto;
    private List<BusquedaCancionDTO> canciones;

    public BusquedaAlbumDTO(String nombreAlbum, String nombreArtista, LocalDate fechaPublicacion, String urlFoto, List<BusquedaCancionDTO> canciones) {
        this.nombreAlbum = nombreAlbum;
        this.nombreArtista = nombreArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.urlFoto = urlFoto;
        this.canciones = canciones;
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

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
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
