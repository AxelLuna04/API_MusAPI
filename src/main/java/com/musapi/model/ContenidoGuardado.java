/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.model;

import jakarta.persistence.*;

/**
 *
 * @author luisp
 */
@Entity
@Table
public class ContenidoGuardado {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "idContenidoGuardado")
    private Integer idContenidoGuardado;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idListaDeReproduccion", nullable = true)
    private ListaDeReproduccion listaDeReproduccion;
    
    @ManyToOne
    @JoinColumn(name = "idCancion", nullable = true)
    private Cancion cancion;

    @ManyToOne
    @JoinColumn(name = "idAlbum", nullable = true)
    private Album album;
        
    @ManyToOne
    @JoinColumn(name = "idPerfilArtista", nullable = true)
    private PerfilArtista perfilArtista;

    //Gettes y setters

    public Integer getIdContenidoGuardado() {
        return idContenidoGuardado;
    }

    public void setIdContenidoGuardado(Integer idContenidoGuardado) {
        this.idContenidoGuardado = idContenidoGuardado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ListaDeReproduccion getListaDeReproduccion() {
        return listaDeReproduccion;
    }

    public void setListaDeReproduccion(ListaDeReproduccion listaDeReproduccion) {
        this.listaDeReproduccion = listaDeReproduccion;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public PerfilArtista getPerfilArtista() {
        return perfilArtista;
    }

    public void setPerfilArtista(PerfilArtista perfilArtista) {
        this.perfilArtista = perfilArtista;
    }
    
    
}