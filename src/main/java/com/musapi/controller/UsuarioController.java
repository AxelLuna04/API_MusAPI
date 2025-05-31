/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaArtistaDTO;
import com.musapi.dto.EdicionPerfilDTO;
import com.musapi.dto.LoginRequest;
import com.musapi.dto.PerfilArtistaDTO;
import com.musapi.dto.RespuestaDTO;
import com.musapi.dto.UsuarioDTO;
import com.musapi.model.Usuario;
import com.musapi.repository.UsuarioRepository;
import com.musapi.security.JwtUtils;
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
    
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registrar")
    public ResponseEntity<RespuestaDTO<Usuario>> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            String contrasenaSinHash = usuario.getContrasenia();
            String contrasenaHasheada = passwordEncoder.encode(contrasenaSinHash);
            usuario.setContrasenia(contrasenaHasheada);
            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(new RespuestaDTO<>("Usuario registrado con éxito.", usuarioGuardado));
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(new RespuestaDTO<>("Error al registrar usuario.", null));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO<UsuarioDTO>> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByCorreo(loginRequest.getCorreo());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RespuestaDTO<>("Correo no encontrado.", null));
        }

        boolean contrasenaValida = passwordEncoder.matches(loginRequest.getContrasenia(), usuario.getContrasenia());

        if (!contrasenaValida) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new RespuestaDTO<>("Contraseña incorrecta.", null));
        }
        String token = jwtUtils.generarToken(usuario.getCorreo());
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setPais(usuario.getPais());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setEsArtista(usuario.getEsArtista());
        usuarioDTO.setEsAdmin(usuario.getEsAdmin());
        usuarioDTO.setToken(token);
        
        return ResponseEntity.ok(new RespuestaDTO<>("Login exitoso.", usuarioDTO));
    }

    
    @PutMapping("/{id}/editar-perfil")
    public ResponseEntity<RespuestaDTO<String>> editarPerfil(@PathVariable Integer id, @RequestBody EdicionPerfilDTO edicionPerfil){
        try {
            usuarioService.editarPerfil(id, edicionPerfil);
            return ResponseEntity.ok(new RespuestaDTO<>("Perfil editado con éxito.", null));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new RespuestaDTO<>(ex.getMessage(), null));
        }
    }

    @GetMapping("/artistas/buscar")
    public ResponseEntity<RespuestaDTO<List<BusquedaArtistaDTO>>> buscarArtistasPorNombreUsuario(@RequestParam String nombreUsuario) {
        try {
            List<BusquedaArtistaDTO> artistas = usuarioService.buscarArtistasPorNombreUsuario(nombreUsuario);
            return ResponseEntity.ok(new RespuestaDTO<>("Artistas encontrados.", artistas));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al buscar artistas.", null));
        }
    }

    @PostMapping("/crear-perfilArtista")
    public ResponseEntity<RespuestaDTO<Void>> crearPerfilArtista(@ModelAttribute PerfilArtistaDTO perfilArtistaDTO) {
        try {
            usuarioService.crearPerfilArtista(perfilArtistaDTO);
            return ResponseEntity.ok(new RespuestaDTO<>("Perfil de artista creado exitosamente.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                new RespuestaDTO<>(e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new RespuestaDTO<>("Error interno del servidor. No se pudo crear el perfil de artista.", null)
            );
        }
    }
    
    @DeleteMapping("/{id}/eliminar-usuario")
    public ResponseEntity<RespuestaDTO<Void>> eliminarUsuario(
            @PathVariable Integer id,
            @RequestParam String motivo) {
        try {
            usuarioService.eliminarUsuario(id, motivo);
            return ResponseEntity.ok(new RespuestaDTO<>("Usuario eliminado correctamente.", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RespuestaDTO<>("Error al eliminar el usuario.", null));
        }
    }

}
