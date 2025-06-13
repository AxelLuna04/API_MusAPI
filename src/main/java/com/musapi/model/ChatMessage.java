/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

/**
 *
 * @author axell
 */
public class ChatMessage {
    private String nombreUsuario;
    private String mensaje;
    private Integer idPerfilArtista;

    // Getters y setters
    
    public ChatMessage() {
    }

    public ChatMessage(String nombreUsuario, String mensaje, Integer idPerfilArtista) {
        this.nombreUsuario = nombreUsuario;
        this.mensaje = mensaje;
        this.idPerfilArtista = idPerfilArtista;
    }

    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdPerfilArtista() {
        return idPerfilArtista;
    }

    public void setIdPerfilArtista(Integer idPerfilArtista) {
        this.idPerfilArtista = idPerfilArtista;
    }
    
    
}

