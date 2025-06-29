/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.grpc;

import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author axell
 */
@GrpcService
public class CategoriaMusicalGrpcService extends CategoriaMusicalServiceGrpc.CategoriaMusicalServiceImplBase {

    @Autowired
    private CategoriaMusicalRepository categoriaMusicalRepository;

    @Override
    public void registrarCategoriaMusical(CategoriaMusicalRequest request, StreamObserver<CategoriaMusicalResponse> responseObserver) {
        CategoriaMusical nueva = new CategoriaMusical();
        nueva.setNombre(request.getNombre());
        nueva.setDescripcion(request.getDescripcion());

        CategoriaMusical guardada = categoriaMusicalRepository.save(nueva);

        CategoriaMusicalResponse response = CategoriaMusicalResponse.newBuilder()
                .setIdCategoriaMusical(guardada.getIdCategoriaMusical())
                .setNombre(guardada.getNombre())
                .setDescripcion(guardada.getDescripcion())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void obtenerCategoriasMusicales(Empty request, StreamObserver<CategoriaMusicalListResponse> responseObserver) {
        List<CategoriaMusical> categorias = categoriaMusicalRepository.findAll();

        CategoriaMusicalListResponse.Builder responseBuilder = CategoriaMusicalListResponse.newBuilder();
        for (CategoriaMusical cat : categorias) {
            CategoriaMusicalResponse item = CategoriaMusicalResponse.newBuilder()
                .setIdCategoriaMusical(cat.getIdCategoriaMusical())
                .setNombre(cat.getNombre())
                .setDescripcion(cat.getDescripcion())
                .build();
            responseBuilder.addCategorias(item);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void editarCategoriaMusical(CategoriaMusicalEditRequest request, StreamObserver<CategoriaMusicalResponse> responseObserver) {
        Optional<CategoriaMusical> optional = categoriaMusicalRepository.findById(request.getIdCategoriaMusical());

        if (!optional.isPresent()) {
            responseObserver.onError(Status.NOT_FOUND
                .withDescription("Categoría no encontrada")
                .asRuntimeException());
            return;
        }

        CategoriaMusical existente = optional.get();
        existente.setNombre(request.getNombre());
        existente.setDescripcion(request.getDescripcion());

        CategoriaMusical actualizada = categoriaMusicalRepository.save(existente);

        CategoriaMusicalResponse response = CategoriaMusicalResponse.newBuilder()
            .setIdCategoriaMusical(actualizada.getIdCategoriaMusical())
            .setNombre(actualizada.getNombre())
            .setDescripcion(actualizada.getDescripcion())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}

