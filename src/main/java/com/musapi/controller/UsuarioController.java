/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.EdicionPerfilDTO;
import com.musapi.dto.LoginRequest;
import com.musapi.model.Usuario;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/usuarios")
/**
 *
 * @author jarly
 */
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
     
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        String contrasenaSinHash = usuario.getContrasena();
        String contrasenaHasheada = passwordEncoder.encode(contrasenaSinHash);
        usuario.setContrasena(contrasenaHasheada);
        return usuarioRepository.save(usuario);
    }

    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }
     
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    if(usuarioActualizado.getNombre() != null) {
                        usuarioExistente.setNombre(usuarioActualizado.getNombre());
                    }
                    if(usuarioActualizado.getCorreo()!= null) {
                        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
                    }
                    if(usuarioActualizado.getNombreUsuario()!= null) {
                        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                    }
                    if(usuarioActualizado.getPais()!= null) {
                        usuarioExistente.setPais(usuarioActualizado.getPais());
                    }
                    if(usuarioActualizado.getEsAdmin() != null) {
                        usuarioExistente.setEsAdmin(usuarioActualizado.getEsAdmin());
                    }
                    if(usuarioActualizado.getEsArtista()!= null) {
                        usuarioExistente.setEsArtista(usuarioActualizado.getEsArtista());
                    }
                    
                    return ResponseEntity.ok(usuarioRepository.save(usuarioExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if(usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(0);
        }

        boolean contrasenaValida = passwordEncoder.matches(loginRequest.getContrasena(), usuario.getContrasena());

        if (!contrasenaValida) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(1);
        }

        return ResponseEntity.ok(usuario);
    }
    
    @PutMapping("/{id}/editar-perfil")
    public ResponseEntity<?> editarPerfil(@PathVariable Integer id, @RequestBody EdicionPerfilDTO edicionPerfil){
        try{
            usuarioService.editarPerfil(id, edicionPerfil);
            return ResponseEntity.ok("Perfil editado con exito.");
        }catch(Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @GetMapping("/artistas/buscar")
    public ResponseEntity<?> buscarArtistasPorNombreUsuario(@RequestParam String nombreUsuario) {
        try {
            List<BusquedaArtistaDTO> artistas = usuarioService.buscarArtistasPorNombreUsuario(nombreUsuario);
            return ResponseEntity.ok(artistas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar artistas.");
        }
    }
}
