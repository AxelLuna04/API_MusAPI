/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jarly
 */
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(length = 100, nullable = true)
    private String nombre;

    @Column(length = 320, nullable = false, unique = true)
    private String correo;

    @Column(name = "nombreUsuario", length = 30, nullable = false, unique = true)
    private String nombreUsuario;

    @Column(length = 100, nullable = false)
    private String pais;

    @Column(name = "esAdmin", nullable = false)
    private Boolean esAdmin;

    @Column(name = "esArtista", nullable = false)
    private Boolean esArtista;
    
    @Column(length = 255, nullable = false)
    private String contrasenia;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDeReproduccion> listasDeReproduccion = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones = new ArrayList<>();
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilArtista perfilArtista;
    
    // Getters y setters

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public Boolean getEsArtista() {
        return esArtista;
    }

    public void setEsArtista(Boolean esArtista) {
        this.esArtista = esArtista;
    }
    
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasena) {
        this.contrasenia = contrasena;
    }

    public List<ListaDeReproduccion> getListasDeReproduccion() {
        return listasDeReproduccion;
    }

    public void setListasDeReproduccion(List<ListaDeReproduccion> listasDeReproduccion) {
        this.listasDeReproduccion = listasDeReproduccion;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public PerfilArtista getPerfilArtista() {
        return perfilArtista;
    }

    public void setPerfilArtista(PerfilArtista perfilArtista) {
        this.perfilArtista = perfilArtista;
    }
       
}
