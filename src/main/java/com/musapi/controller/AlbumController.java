/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.controller;

import com.musapi.dto.BusquedaAlbumDTO;
import com.musapi.service.AlbumService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author axell
 */
@RestController
@RequestMapping("/api/albumes")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    
    @GetMapping("/buscar")
    public List<BusquedaAlbumDTO> buscarAlbumes(@RequestParam("texto") String texto){
        return albumService.buscarAlbumesPorNombre(texto);
    }
}
