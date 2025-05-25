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
    private byte[] foto;
    private List<BusquedaCancionDTO> canciones;

    public BusquedaAlbumDTO(String nombreAlbum, String nombreArtista, LocalDate fechaPublicacion, byte[] foto, List<BusquedaCancionDTO> canciones) {
        this.nombreAlbum = nombreAlbum;
        this.nombreArtista = nombreArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.foto = foto;
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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public List<BusquedaCancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<BusquedaCancionDTO> canciones) {
        this.canciones = canciones;
    }

    
    
    
    
}
