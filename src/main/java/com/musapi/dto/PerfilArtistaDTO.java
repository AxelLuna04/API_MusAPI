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
public class PerfilArtistaDTO {
    private Integer idUsuario;
    private String descripcion;
    private MultipartFile foto;

    public PerfilArtistaDTO(Integer idUsuario, String descripcion, MultipartFile foto) {
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }
    
    
}
