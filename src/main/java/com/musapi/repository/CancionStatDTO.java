package com.musapi.repository;

import com.musapi.model.Cancion;

public interface CancionStatDTO {
    Cancion getCancion();
    Long getTotalEscuchas();
    Long getTotalGuardados();
}