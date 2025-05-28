/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.CreacionListaDeReproduccionDTO;
import com.musapi.model.ListaDeReproduccion;
import com.musapi.model.Usuario;
import com.musapi.repository.ListaDeReproduccionRepository;
import com.musapi.repository.UsuarioRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class ListaDeReproduccionService {
    
    @Autowired
    private ListaDeReproduccionRepository listaDeReproduccionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public String crearListaDeReproduccion(CreacionListaDeReproduccionDTO creacionListaDeReproduccionDTO){
        Usuario usuario = usuarioRepository.findByIdUsuario(creacionListaDeReproduccionDTO.getIdUsuario());
            
        if (usuario == null) {
            return "Usuario no encontrado.";
        }
            
        ListaDeReproduccion listaDeReproduccion = new ListaDeReproduccion();
        listaDeReproduccion.setNombre(creacionListaDeReproduccionDTO.getNombre());
        listaDeReproduccion.setDescripcion(creacionListaDeReproduccionDTO.getDescripcion());
        listaDeReproduccion.setUsuario(usuario);
        
        if (creacionListaDeReproduccionDTO.getFoto() != null) {
            String nombreArchivo = "foto_" + usuario.getIdUsuario() + creacionListaDeReproduccionDTO.getNombre() + "_" + System.currentTimeMillis() + ".jpg";
            String rutaDestino = "uploads/fotos-listasDeReproduccion/" + nombreArchivo;
            java.io.File destino = new java.io.File(rutaDestino);
            
            destino.getParentFile().mkdirs();
            
            try {
                creacionListaDeReproduccionDTO.getFoto().transferTo(destino);
                listaDeReproduccion.setUrlFoto("/" + rutaDestino);
            } catch (IOException e) {
                throw new IllegalArgumentException("Error al guardar la imagen.");
            }
        }
            
        listaDeReproduccionRepository.save(listaDeReproduccion);
        return "Lista de reproducción creada";
    }
}
