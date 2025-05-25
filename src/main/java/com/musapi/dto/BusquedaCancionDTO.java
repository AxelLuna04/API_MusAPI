/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author axell
 */
public class BusquedaCancionDTO {
    private Integer idCancion;
    private String nombre;
    private LocalTime duracion;
    private byte[] archivo;
    private byte[] foto;
    private String nombreArtista;
    private LocalDate fechaPublicacion;
    private String nombreAlbum;

    public BusquedaCancionDTO(Integer idCancion, String nombre, LocalTime duracion, byte[] archivo, byte[] foto, String nombreArtista, LocalDate fechaPublicacion, String nombreAlbum) {
        this.idCancion = idCancion;
        this.nombre = nombre;
        this.duracion = duracion;
        this.archivo = archivo;
        this.foto = foto;
        this.nombreArtista = nombreArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.nombreAlbum = nombreAlbum;
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

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
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

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    
    
}
