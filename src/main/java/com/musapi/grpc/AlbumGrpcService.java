package com.musapi.grpc;

import com.musapi.model.Album;
import com.musapi.repository.AlbumRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
public class AlbumGrpcService extends AlbumServiceGrpc.AlbumServiceImplBase {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public void obtenerAlbumesPublicos(EmptyRequest request, StreamObserver<AlbumListResponse> responseObserver) {
        List<Album> albumes = albumRepository.findByEstado("publico");

        AlbumListResponse.Builder response = AlbumListResponse.newBuilder();

        for (Album album : albumes) {
            AlbumDTO dto = AlbumDTO.newBuilder()
                    .setIdAlbum(album.getIdAlbum())
                    .setNombre(album.getNombre())
                    .setNombreArtista(album.getPerfilArtista().getUsuario().getNombreUsuario())
                    .setUrlFoto(album.getUrlFoto() == null ? "" : album.getUrlFoto())
                    .setFechaPublicacion(album.getFechaPublicacion() == null ? "" : album.getFechaPublicacion().toString())
                    .build();

            response.addAlbumes(dto);
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}

