/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.service;

import com.musapi.dto.EscuchaDTO;
import com.musapi.model.Cancion;
import com.musapi.model.Escucha;
import com.musapi.model.Usuario;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.EscuchaRepository;
import com.musapi.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author axell
 */
@Service
public class EscuchaService {
    
    @Autowired
    private EscuchaRepository escuchaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CancionRepository cancionRepository;
    
    public void registrarEscucha(EscuchaDTO escuchaDTO) {
        Usuario usuario = usuarioRepository.findById(escuchaDTO.getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Cancion cancion = cancionRepository.findById(escuchaDTO.getIdCancion())
                .orElseThrow(() -> new IllegalArgumentException("Canción no encontrada"));

        Escucha escucha = new Escucha();
        escucha.setUsuario(usuario);
        escucha.setCancion(cancion);
        escucha.setFechaEscucha(LocalDate.now());
        
        int horas = escuchaDTO.getSegundosEscucha() / 3600;
        int minutos = (escuchaDTO.getSegundosEscucha() % 3600) / 60;
        int segundos = escuchaDTO.getSegundosEscucha() % 60;
        LocalTime tiempoEscucha = LocalTime.of(horas, minutos, segundos);
        
        escucha.setTiempoEscucha(tiempoEscucha);

        escuchaRepository.save(escucha);
    }
}
