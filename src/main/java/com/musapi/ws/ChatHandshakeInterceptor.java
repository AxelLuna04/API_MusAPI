// src/main/java/com/musapi/ws/ChatHandshakeInterceptor.java
package com.musapi.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class ChatHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // URI: /ws/{idPerfilArtista}
        String path = request.getURI().getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        try {
            attributes.put("idPerfilArtista", Integer.parseInt(idStr));
            return true;
        } catch (NumberFormatException e) {
            return false; 
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) { }
}
