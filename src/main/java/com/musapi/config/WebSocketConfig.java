// src/main/java/com/musapi/config/WebSocketConfig.java
package com.musapi.config;

import com.musapi.ws.ChatHandshakeInterceptor;
import com.musapi.ws.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
          .addHandler(new ChatWebSocketHandler(), "/ws/{idPerfilArtista}")
          .addInterceptors(new ChatHandshakeInterceptor())
          .setAllowedOriginPatterns("*");
    }
}
