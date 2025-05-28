/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author axell
 */
public class AlbumDTO {
    private String nombre;
    private Integer idPerfilArtista;
    private String fechaPublicacion;
    private MultipartFile foto;

    public AlbumDTO(String nombre, Integer idPerfilArtista, String fechaPublicacion, MultipartFile foto) {
        this.nombre = nombre;
        this.idPerfilArtista = idPerfilArtista;
        this.fechaPublicacion = fechaPublicacion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdPerfilArtista() {
        return idPerfilArtista;
    }

    public void setIdPerfilArtista(Integer idPerfilArtista) {
        this.idPerfilArtista = idPerfilArtista;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
    
    
}
