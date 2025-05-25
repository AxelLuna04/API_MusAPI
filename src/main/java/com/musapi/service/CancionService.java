/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.BusquedaCancionDTO;
import com.musapi.model.Cancion;
import com.musapi.repository.CancionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class CancionService {
    
    @Autowired
    private CancionRepository cancionRepository;
    
    public List<BusquedaCancionDTO> buscarCancionesPorNombre(String texto){
        List<Cancion> cancionesEncontradas = cancionRepository.findByNombreContainingIgnoreCase(texto);
        
        return cancionesEncontradas.stream()
                .map(cancion -> {
                    String nombreArtista = cancion.getPerfilArtista_CancionList().isEmpty()
                            ? null
                            : cancion.getPerfilArtista_CancionList().get(0).getPerfilArtista().getUsuario().getNombreUsuario();
                    
                    return new BusquedaCancionDTO(
                            cancion.getIdCancion(),
                            cancion.getNombre(),
                            cancion.getDuracion(),
                            cancion.getArchivo(),
                            cancion.getArchivo(),
                            nombreArtista,
                            cancion.getFechaPublicacion(),
                            cancion.getAlbum().getNombre()
                    );
                })
                .collect(Collectors.toList());
    }
}
