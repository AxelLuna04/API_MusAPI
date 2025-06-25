package com.musapi;

import com.musapi.dto.EscuchaDTO;
import com.musapi.model.Cancion;
import com.musapi.model.Escucha;
import com.musapi.model.Usuario;
import com.musapi.repository.CancionRepository;
import com.musapi.repository.EscuchaRepository;
import com.musapi.repository.UsuarioRepository;
import com.musapi.service.EscuchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EscuchaServiceTests {

    @InjectMocks
    private EscuchaService escuchaService;

    @Mock
    private EscuchaRepository escuchaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CancionRepository cancionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registrarEscucha_deberiaGuardarEscuchaCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        Cancion cancion = new Cancion();
        cancion.setIdCancion(2);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cancionRepository.findById(2)).thenReturn(Optional.of(cancion));

        EscuchaDTO dto = new EscuchaDTO();
        dto.setIdUsuario(1);
        dto.setIdCancion(2);
        dto.setSegundosEscucha(125); // 2 minutos y 5 segundos

        escuchaService.registrarEscucha(dto);

        ArgumentCaptor<Escucha> captor = ArgumentCaptor.forClass(Escucha.class);
        verify(escuchaRepository).save(captor.capture());

        Escucha escuchaGuardada = captor.getValue();
        assertEquals(usuario, escuchaGuardada.getUsuario());
        assertEquals(cancion, escuchaGuardada.getCancion());
        assertEquals("00:02:05", escuchaGuardada.getTiempoEscucha().toString());
    }

    @Test
    public void registrarEscucha_deberiaLanzarExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        EscuchaDTO dto = new EscuchaDTO();
        dto.setIdUsuario(999);
        dto.setIdCancion(1);
        dto.setSegundosEscucha(60);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            escuchaService.registrarEscucha(dto);
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
    }

    @Test
    public void registrarEscucha_deberiaLanzarExcepcionSiCancionNoExiste() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(cancionRepository.findById(999)).thenReturn(Optional.empty());

        EscuchaDTO dto = new EscuchaDTO();
        dto.setIdUsuario(1);
        dto.setIdCancion(999);
        dto.setSegundosEscucha(45);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            escuchaService.registrarEscucha(dto);
        });

        assertEquals("Canción no encontrada", ex.getMessage());
    }
}
