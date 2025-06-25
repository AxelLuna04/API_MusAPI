package com.musapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musapi.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @Transactional
    @Rollback
    void deberiaRegistrarUsuarioConExito() throws Exception {
        String json = """
        {
            "nombre": "Juan Pérez",
            "correo": "juan@example.com",
            "nombreUsuario": "juan123",
            "pais": "Mexico",
            "esAdmin": false,
            "esArtista": false,
            "contrasenia": "1234"
        }
    """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Usuario registrado con éxito."))
                .andExpect(jsonPath("$.datos.nombreUsuario").value("juan123"));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaRetornarConflictSiNombreUsuarioYaExiste() throws Exception {
        String primero = """
        {
            "nombre": "Juan Pérez",
            "correo": "juan@example.com",
            "nombreUsuario": "juan123",
            "pais": "Mexico",
            "esAdmin": false,
            "esArtista": false,
            "contrasenia": "1234"
        }
    """;
        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(primero)).andExpect(status().isOk());

        String duplicado = """
        {
            "nombre": "Juan Pérez",
            "correo": "juan@example1.com",
            "nombreUsuario": "juan123",
            "pais": "Mexico",
            "esAdmin": false,
            "esArtista": false,
            "contrasenia": "1234"
        }
    """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicado))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("El nombre de usuario ya está en uso."));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaRetornarConflictSiCorreoYaExiste() throws Exception {
        String primero = """
        {
            "nombre": "Carlos",
            "correo": "carlos@example.com",
            "nombreUsuario": "carlos1",
            "pais": "MX",
            "contrasenia": "5678"
        }
    """;
        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(primero)).andExpect(status().isOk());

        String duplicado = """
        {
            "nombre": "Elena",
            "correo": "carlos@example.com",
            "nombreUsuario": "elena2",
            "pais": "MX",
            "contrasenia": "abcd"
        }
    """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicado))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("El correo ya está registrado."));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaIniciarSesionConExito() throws Exception {
        String registroJson = """
        {
            "nombre": "Luis López",
            "correo": "luis@example.com",
            "nombreUsuario": "luisito",
            "contrasenia": "12345",
            "pais": "MX"
        }
    """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk());

        String loginJson = """
        {
            "correo": "luis@example.com",
            "contrasenia": "12345"
        }
    """;

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Login exitoso."))
                .andExpect(jsonPath("$.datos.token").exists())
                .andExpect(jsonPath("$.datos.nombreUsuario").value("luisito"));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaFallarLoginPorCorreoNoExistente() throws Exception {
        String loginJson = """
        {
            "correo": "noexiste@example.com",
            "contrasenia": "12345"
        }
    """;

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensaje").value("Correo no encontrado."))
                .andExpect(jsonPath("$.datos").doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    void deberiaFallarLoginPorContrasenaIncorrecta() throws Exception {
        String registroJson = """
        {
            "nombre": "Ana Torres",
            "correo": "ana@example.com",
            "nombreUsuario": "anaT",
            "contrasenia": "correcta",
            "pais": "MX"
        }
    """;

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk());

        String loginJson = """
        {
            "correo": "ana@example.com",
            "contrasenia": "incorrecta"
        }
    """;

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensaje").value("Contraseña incorrecta."))
                .andExpect(jsonPath("$.datos").doesNotExist());
    }

    @Test
    @Transactional
    @Rollback
    void deberiaEditarPerfilConExito() throws Exception {
        String registroJson = """
    {
        "nombre": "Carlos Rivera",
        "correo": "carlos@example.com",
        "nombreUsuario": "carlos123",
        "contrasenia": "abc123",
        "pais": "MX"
    }
    """;

        // Registrar usuario
        MvcResult registroResult = mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = registroResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        int idUsuario = jsonNode.get("datos").get("idUsuario").asInt();

        // Login para obtener token
        String loginJson = """
    {
        "correo": "carlos@example.com",
        "contrasenia": "abc123"
    }
    """;

        MvcResult loginResult = mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String token = new ObjectMapper()
                .readTree(loginResult.getResponse().getContentAsString())
                .get("datos")
                .get("token")
                .asText();

        // Crear partes del formulario
        MockMultipartFile nombrePart = new MockMultipartFile("nombre", "", "text/plain", "Carlos Editado".getBytes());
        MockMultipartFile nombreUsuarioPart = new MockMultipartFile("nombreUsuario", "", "text/plain", "carlosEdit".getBytes());
        MockMultipartFile paisPart = new MockMultipartFile("pais", "", "text/plain", "México".getBytes());
        MockMultipartFile descripcionPart = new MockMultipartFile("descripcion", "", "text/plain", "Descripción actualizada".getBytes());

        // Realizar petición PUT multipart con token
        mockMvc.perform(multipart("/api/usuarios/{id}/editar-perfil", idUsuario)
                .file(nombrePart)
                .file(nombreUsuarioPart)
                .file(paisPart)
                .file(descripcionPart)
                .with(request -> {
                    request.setMethod("PUT");
                    request.addHeader("Authorization", "Bearer " + token);
                    return request;
                }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Perfil editado con éxito."))
                .andExpect(jsonPath("$.datos").isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void deberiaBuscarArtistasPorNombreUsuario() throws Exception {
        // 1. Registrar usuario
        String registroJson = """
    {
        "nombre": "Luis Fonsi",
        "correo": "fonsi@example.com",
        "nombreUsuario": "luisfonsi",
        "contrasenia": "despacito123",
        "pais": "MX"
    }
    """;

        MvcResult registroResult = mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroJson))
                .andExpect(status().isOk())
                .andReturn();

        String registroBody = registroResult.getResponse().getContentAsString();
        int idUsuario = new ObjectMapper()
                .readTree(registroBody)
                .get("datos")
                .get("idUsuario")
                .asInt();

        // 2. Login para obtener token
        String loginJson = """
    {
        "correo": "fonsi@example.com",
        "contrasenia": "despacito123"
    }
    """;

        MvcResult loginResult = mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String token = new ObjectMapper()
                .readTree(loginResult.getResponse().getContentAsString())
                .get("datos")
                .get("token")
                .asText();

        // 3. Crear perfil de artista usando el token
        mockMvc.perform(multipart("/api/usuarios/crear-perfilArtista")
                .param("idUsuario", String.valueOf(idUsuario))
                .param("nombre", "Luis Fonsi")
                .param("descripcion", "Artista musical")
                .with(request -> {
                    request.addHeader("Authorization", "Bearer " + token);
                    return request;
                }))
                .andExpect(status().isOk());

        // 4. Buscar artistas con el token
        mockMvc.perform(get("/api/usuarios/artistas/buscar")
                .param("texto", "luis")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Artistas encontrados."))
                .andExpect(jsonPath("$.datos[0].nombreUsuario").value("luisfonsi"));
    }

    @Test
    @Transactional
    @Rollback
    void deberiaRetornarListaVaciaSiNoEncuentraArtistas() throws Exception {
        String token = jwtUtils.generarToken("usuario@ejemplo.com");

        mockMvc.perform(get("/api/usuarios/artistas/buscar")
                .param("texto", "desconocido")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Artistas encontrados."))
                .andExpect(jsonPath("$.datos").isArray())
                .andExpect(jsonPath("$.datos").isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void deberiaEliminarUsuarioConExito() throws Exception {
        String jsonRegistro = """
    {
        "nombre": "María Gómez",
        "correo": "maria@example.com",
        "nombreUsuario": "mariag",
        "contrasenia": "clave123",
        "pais": "MX"
    }
    """;

        MvcResult result = mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRegistro))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        int idUsuario = new ObjectMapper()
                .readTree(body)
                .get("datos")
                .get("idUsuario")
                .asInt();

        String token = jwtUtils.generarToken("maria@example.com");

        mockMvc.perform(delete("/api/usuarios/{id}/eliminar", idUsuario)
                .header("Authorization", "Bearer " + token)
                .param("motivo", "No desea seguir usando la app"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Usuario eliminado con éxito."));
    }

}
