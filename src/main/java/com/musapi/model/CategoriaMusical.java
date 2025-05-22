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
 * @author luisp
 */
@Entity
@Table
public class CategoriaMusical {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "idCategoriaMusical")
    private Integer idCategoriaMusical;
    
    @Column (name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;
    
    @Column (length = 300, nullable = false)
    private String descripcion;
    
    @OneToMany(mappedBy = "categoriaMusical", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cancion> canciones = new ArrayList<>();

    //Getters y setters
    
    public Integer getIdCategoriaMusical() {
        return idCategoriaMusical;
    }

    public void setIdCategoriaMusical(Integer idCategoriaMusical) {
        this.idCategoriaMusical = idCategoriaMusical;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }
    
    
}
