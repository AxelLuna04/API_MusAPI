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
import com.musapi.model.PerfilArtista;
import com.musapi.model.Usuario;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.security.JwtUtils;
import com.musapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

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
        Usuario existePorNombreUsuario = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        if (existePorNombreUsuario != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RespuestaDTO<>("El nombre de usuario ya está en uso.", null));
        }

        Usuario existePorCorreo = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existePorCorreo != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RespuestaDTO<>("El correo ya está registrado.", null));
        }

        usuario.setEsAdmin(false);
        usuario.setEsArtista(false);

        String contrasenaSinHash = usuario.getContrasenia();
        String contrasenaHasheada = passwordEncoder.encode(contrasenaSinHash);
        usuario.setContrasenia(contrasenaHasheada);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(new RespuestaDTO<>("Usuario registrado con éxito.", usuarioGuardado));
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

    @PutMapping(value = "/{id}/editar-perfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RespuestaDTO<String>> editarPerfil(
            @PathVariable Integer id,
            @RequestPart(value = "nombre", required = false) String nombre,
            @RequestPart(value = "nombreUsuario", required = false) String nombreUsuario,
            @RequestPart(value = "pais", required = false) String pais,
            @RequestPart(value = "descripcion", required = false) String descripcion,
            @RequestPart(value = "foto", required = false) MultipartFile foto
    ) {
        EdicionPerfilDTO edicionPerfil = new EdicionPerfilDTO();
        if (nombre != null) {
            edicionPerfil.setNombre(nombre);
        }
        if (nombreUsuario != null) {
            edicionPerfil.setNombreUsuario(nombreUsuario);
        }
        if (pais != null) {
            edicionPerfil.setPais(pais);
        }
        if (descripcion != null) {
            edicionPerfil.setDescripcion(descripcion);
        }
        if (foto != null) {
            edicionPerfil.setFoto(foto);
        }

        usuarioService.editarPerfil(id, edicionPerfil);
        return ResponseEntity.ok(new RespuestaDTO<>("Perfil editado con éxito.", null));
    }

    @GetMapping("/artistas/buscar")
    public ResponseEntity<RespuestaDTO<List<BusquedaArtistaDTO>>> buscarArtistasPorNombreUsuario(@RequestParam("texto") String texto) {
        List<BusquedaArtistaDTO> artistas = usuarioService.buscarArtistasPorNombreUsuario(texto);
        return ResponseEntity.ok(new RespuestaDTO<>("Artistas encontrados.", artistas));
    }

    @PostMapping("/crear-perfilArtista")
    public ResponseEntity<RespuestaDTO<Void>> crearPerfilArtista(@ModelAttribute PerfilArtistaDTO perfilArtistaDTO) {
        usuarioService.crearPerfilArtista(perfilArtistaDTO);
        return ResponseEntity.ok(new RespuestaDTO<>("Perfil de artista creado exitosamente.", null));
    }

    @GetMapping("/artista/{id}")
    public ResponseEntity<RespuestaDTO<BusquedaArtistaDTO>> obtenerArtistaPorId(@PathVariable Integer id) {
        BusquedaArtistaDTO artista = usuarioService.obtenerArtistaPorId(id);
        return ResponseEntity.ok(new RespuestaDTO<>("Artista encontrado.", artista));
    }

    @GetMapping("/buscar")
    public ResponseEntity<RespuestaDTO<List<UsuarioDTO>>> buscarUsuario(
            @RequestParam("texto") String texto,
            @RequestParam("idUsuario") Integer idUsuario) {
        List<UsuarioDTO> usuarios = usuarioService.buscarUsuarios(texto, idUsuario);
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new RespuestaDTO<>("No se encontraron usuarios.", usuarios));
        }
        return ResponseEntity.ok(new RespuestaDTO<>("Usuarios encontrados exitosamente.", usuarios));
    }

    @DeleteMapping("/{idUsuario}/eliminar")
    public ResponseEntity<RespuestaDTO<Void>> eliminarUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam("motivo") String motivo) {
        usuarioService.eliminarUsuario(idUsuario, motivo);
        return ResponseEntity.ok(new RespuestaDTO<>("Usuario eliminado con éxito.", null));
    }

}
