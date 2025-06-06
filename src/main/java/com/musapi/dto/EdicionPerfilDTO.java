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
public class EdicionPerfilDTO {
    private String nombre;
    private String nombreUsuario;
    private String pais;
    private String descripcion;
    private MultipartFile foto;

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String direccion) {
        this.descripcion = direccion;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
    
    
    
    
}
