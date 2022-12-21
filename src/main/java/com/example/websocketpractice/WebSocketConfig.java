package com.example.websocketpractice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import reactor.netty.tcp.SslProvider;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ReactorNettyTcpClient<byte[]> client = new ReactorNettyTcpClient<>(tcpClient -> tcpClient
            .host(host)
            .port(61613)
            .secure(SslProvider.defaultClientProvider()), new StompReactorNettyCodec());

        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/queue", "/topic")
            .setAutoStartup(true)
            .setSystemLogin(username)
            .setSystemPasscode(password)
            .setClientLogin(username)
            .setClientPasscode(password)
            .setTcpClient(client);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
        registry.addEndpoint("/gs-guide-websocket");
    }
}
