/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.dto;

/**
 *
 * @author axell
 */
public class EvaluacionArtistaDTO {
    private Integer idUsuario;
    private Integer idArtista;
    private String comentario;
    private Integer calificacion;

    public EvaluacionArtistaDTO(Integer idUsuario, Integer idArtista, String comentario, Integer calificacion) {
        this.idUsuario = idUsuario;
        this.idArtista = idArtista;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
    
}
