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
    private Integer idUsuario;
    private MultipartFile foto;

    public AlbumDTO(String nombre, Integer idUsuario, MultipartFile foto) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
    
    
}
