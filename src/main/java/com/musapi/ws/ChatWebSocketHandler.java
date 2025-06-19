// src/main/java/com/musapi/ws/ChatWebSocketHandler.java
package com.musapi.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musapi.model.ChatMessage;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<Integer, List<WebSocketSession>> salas = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Integer idPerfilArtista = (Integer) session.getAttributes().get("idPerfilArtista");
        if (idPerfilArtista != null) {
            salas.putIfAbsent(idPerfilArtista, new CopyOnWriteArrayList<>());
            salas.get(idPerfilArtista).add(session);
            System.out.println("Conectado a sala: " + idPerfilArtista + " - Sesión: " + session.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        salas.values().forEach(lista -> lista.remove(session));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Integer idPerfilArtista = (Integer) session.getAttributes().get("idPerfilArtista");
        if (idPerfilArtista == null) return;

        ChatMessage msg = mapper.readValue(message.getPayload(), ChatMessage.class);
        String json = mapper.writeValueAsString(msg);

        List<WebSocketSession> sesiones = salas.getOrDefault(idPerfilArtista, List.of());
        for (WebSocketSession s : sesiones) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(json));
            }
        }
    }
}

