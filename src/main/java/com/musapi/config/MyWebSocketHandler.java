/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.musapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musapi.dto.ChatMessageDTO;
import com.musapi.model.ChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, List<WebSocketSession>> artistasConectados = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Cliente conectado: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage msg = mapper.readValue(message.getPayload(), ChatMessage.class);
        Integer idPerfilArtista = msg.getIdPerfilArtista();

        artistasConectados.putIfAbsent(idPerfilArtista.toString(), new CopyOnWriteArrayList<>());
        List<WebSocketSession> sesiones = artistasConectados.get(idPerfilArtista.toString());
        if (!sesiones.contains(session)) {
            sesiones.add(session);
        }

        String jsonRespuesta = mapper.writeValueAsString(new ChatMessage(msg.getNombreUsuario(), msg.getMensaje(), msg.getIdPerfilArtista()));

        for (WebSocketSession s : sesiones) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(jsonRespuesta));
            }
        }
    }

}


