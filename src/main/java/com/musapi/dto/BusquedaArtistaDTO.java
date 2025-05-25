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
public class BusquedaArtistaDTO {
    private Integer idArtista;
    private String nombre;
    private String nombreUsuario;
    private String descripcion;
    private byte[] foto;
    private List<BusquedaCancionDTO> canciones;

    public BusquedaArtistaDTO(Integer idArtista, String nombre, String nombreUsuario, String descripcion, byte[] foto, List<BusquedaCancionDTO> canciones) {
        this.idArtista = idArtista;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.descripcion = descripcion;
        this.foto = foto;
        this.canciones = canciones;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public List<BusquedaCancionDTO> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<BusquedaCancionDTO> canciones) {
        this.canciones = canciones;
    }
    
    
}
