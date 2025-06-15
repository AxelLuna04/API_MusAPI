/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.EvaluacionArtistaDTO;
import com.musapi.model.Evaluacion;
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.EvaluacionRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class EvaluacionService {
    
    @Autowired
    private EvaluacionRepository evaluacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PerfilArtistaRepository perfilArtistaRepository;
    
    public String EvaluarArtista(EvaluacionArtistaDTO evaluacionDTO){
        Usuario usuario = usuarioRepository.findByIdUsuario(evaluacionDTO.getIdUsuario());
        
        if(usuario == null)
            return "Usuario no encontrado.";
        
        PerfilArtista perfilArtista = perfilArtistaRepository.findByIdPerfilArtista(evaluacionDTO.getIdArtista());
        
        if(perfilArtista == null)
            return "Artista no encontrado.";
        
        boolean yaEvaluo = false;
        if(evaluacionRepository.findByUsuario_IdUsuarioAndPerfilArtista_IdPerfilArtista(evaluacionDTO.getIdUsuario(), evaluacionDTO.getIdArtista()) != null)
            yaEvaluo = true;
        
        if(yaEvaluo)
            return "Este usuario ya evaluo a este artista.";
        
        Evaluacion evaluacion = new Evaluacion();
        
        evaluacion.setPerfilArtista(perfilArtista);
        evaluacion.setUsuario(usuario);
        evaluacion.setCalificacion(evaluacionDTO.getCalificacion());
        evaluacion.setComentario(evaluacionDTO.getComentario());
        
        evaluacionRepository.save(evaluacion);
        return "Evaluacion registrada con exito.";
        
    }
}
