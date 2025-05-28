/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author axell
 */
public class CancionDTO {
    private String nombre;
    private MultipartFile archivoCancion;
    private MultipartFile foto;
    private String duracionStr;
    private Integer idCategoriaMusical;
    private Integer idAlbum;
    private Integer posicionEnAlbum;
    private List<Integer> idPerfilArtistas;

    public CancionDTO() {
    }

    
    public CancionDTO(String nombre, MultipartFile archivoCancion, MultipartFile foto, String duracionStr, Integer idCategoriaMusical, Integer idAlbum, Integer posicionEnAlbum, List<Integer> idPerfilArtistas) {
        this.nombre = nombre;
        this.archivoCancion = archivoCancion;
        this.foto = foto;
        this.duracionStr = duracionStr;
        this.idCategoriaMusical = idCategoriaMusical;
        this.idAlbum = idAlbum;
        this.posicionEnAlbum = posicionEnAlbum;
        this.idPerfilArtistas = idPerfilArtistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MultipartFile getArchivoCancion() {
        return archivoCancion;
    }

    public void setArchivoCancion(MultipartFile archivoCancion) {
        this.archivoCancion = archivoCancion;
    }

    public MultipartFile getFoto() {
        return foto;
    }

    public void setFoto(MultipartFile foto) {
        this.foto = foto;
    }

    public String getDuracionStr() {
        return duracionStr;
    }

    public void setDuracionStr(String duracionStr) {
        this.duracionStr = duracionStr;
    }

    public Integer getIdCategoriaMusical() {
        return idCategoriaMusical;
    }

    public void setIdCategoriaMusical(Integer idCategoriaMusical) {
        this.idCategoriaMusical = idCategoriaMusical;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Integer getPosicionEnAlbum() {
        return posicionEnAlbum;
    }

    public void setPosicionEnAlbum(Integer posicionEnAlbum) {
        this.posicionEnAlbum = posicionEnAlbum;
    }

    public List<Integer> getIdPerfilArtistas() {
        return idPerfilArtistas;
    }

    public void setIdPerfilArtistas(List<Integer> idPerfilArtistas) {
        this.idPerfilArtistas = idPerfilArtistas;
    }

    
    
}
