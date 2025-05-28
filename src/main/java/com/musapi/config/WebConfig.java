package com.musapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/fotos-artistas/**")
            .addResourceLocations(Paths.get("uploads/fotos-artistas").toAbsolutePath().toUri().toString());

        registry.addResourceHandler("/uploads/fotos-albumes/**")
            .addResourceLocations(Paths.get("uploads/fotos-albumes").toAbsolutePath().toUri().toString());
        
        registry.addResourceHandler("/uploads/fotos-listasDeReproduccion/**")
            .addResourceLocations(Paths.get("uploads/fotos-listasDeReproduccion").toAbsolutePath().toUri().toString());
        
        registry.addResourceHandler("/uploads/fotos-canciones/**")
            .addResourceLocations(Paths.get("uploads/fotos-canciones").toAbsolutePath().toUri().toString());
        
        registry.addResourceHandler("/uploads/archivos-canciones/**")
            .addResourceLocations(Paths.get("uploads/archivos-canciones").toAbsolutePath().toUri().toString());
    }
}

