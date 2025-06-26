/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.grpc;

import com.musapi.model.CategoriaMusical;
import com.musapi.repository.CategoriaMusicalRepository;
import io.grpc.stub.StreamObserver;
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
}

