/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi;

import com.musapi.dto.CategoriaMusicalDTO;
import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import com.musapi.service.CategoriaMusicalService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class CategoriaMusicalServiceTests {

    @InjectMocks
    private CategoriaMusicalService categoriaMusicalService;

    @Mock
    private CategoriaMusicalRepository categoriaMusicalRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerCategorias_deberiaRetornarListaDTOCorrectamente() {
        CategoriaMusical cat1 = new CategoriaMusical();
        cat1.setIdCategoriaMusical(1);
        cat1.setNombre("Rock");
        cat1.setDescripcion("Guitarras eléctricas y energía");

        CategoriaMusical cat2 = new CategoriaMusical();
        cat2.setIdCategoriaMusical(2);
        cat2.setNombre("Pop");
        cat2.setDescripcion("Popular y pegajosa");

        when(categoriaMusicalRepository.findAll()).thenReturn(List.of(cat1, cat2));

        List<CategoriaMusicalDTO> resultado = categoriaMusicalService.obtenerCategorias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        CategoriaMusicalDTO dto1 = resultado.get(0);
        assertEquals(1, dto1.getIdCategoriaMusical());
        assertEquals("Rock", dto1.getNombre());
        assertEquals("Guitarras eléctricas y energía", dto1.getDescripcion());

        CategoriaMusicalDTO dto2 = resultado.get(1);
        assertEquals(2, dto2.getIdCategoriaMusical());
        assertEquals("Pop", dto2.getNombre());
        assertEquals("Popular y pegajosa", dto2.getDescripcion());

        verify(categoriaMusicalRepository).findAll();
    }

    @Test
    public void obtenerCategorias_sinCategorias_deberiaRetornarListaVacia() {
        when(categoriaMusicalRepository.findAll()).thenReturn(List.of());

        List<CategoriaMusicalDTO> resultado = categoriaMusicalService.obtenerCategorias();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(categoriaMusicalRepository).findAll();
    }
}
