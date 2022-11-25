package com.example.demo.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketServerEndpointConfig {

//    @Bean
//    public WebSocketServerEndpoint reverseWebSocketEndpoint() {
//        return new WebSocketServerEndpoint();
//    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}