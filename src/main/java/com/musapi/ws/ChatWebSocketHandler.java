// src/main/java/com/musapi/ws/ChatWebSocketHandler.java
package com.musapi.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musapi.model.ChatMessage;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions =
        Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Integer salaOrigen = (Integer) session.getAttributes().get("idPerfilArtista");

        for (WebSocketSession s : sessions) {
            Integer salaDestino = (Integer) s.getAttributes().get("idPerfilArtista");
            if (s.isOpen() && salaOrigen != null && salaOrigen.equals(salaDestino)) {
                s.sendMessage(message);
            }
        }
    }
}
