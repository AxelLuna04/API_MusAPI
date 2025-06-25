/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musapi.model.Album;
import com.musapi.model.PerfilArtista;
import com.musapi.repository.AlbumRepository;
import com.musapi.repository.PerfilArtistaRepository;
import com.musapi.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
private UsuarioRepository usuarioRepository;
    
        @Autowired
private AlbumRepository albumRepository;


    @Test
    @Transactional
    @Rollback
    void deberiaRetornarVacioSiNoHayAlbumesPublicos() throws Exception {
        String token = obtenerToken();

        mockMvc.perform(get("/api/albumes/publicos")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("No hay álbumes públicos disponibles"));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaRetornarListaVaciaAlBuscarAlbumInexistente() throws Exception {
        String token = obtenerToken();

        mockMvc.perform(get("/api/albumes/buscar")
                .param("texto", "desconocido")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("No se encontraron álbumes"));
    }

    private String obtenerToken() throws Exception {
        String registroJson = """
        {
            "nombre": "Token User",
            "correo": "token@example.com",
            "nombreUsuario": "tokenuser",
            "contrasenia": "clave123",
            "pais": "MX"
        }
        """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk());

        String loginJson = """
        {
            "correo": "token@example.com",
            "contrasenia": "clave123"
        }
        """;

        MvcResult loginResult = mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        return new ObjectMapper()
                .readTree(loginResult.getResponse().getContentAsString())
                .get("datos").get("token").asText();
    }
    
    
    
}

