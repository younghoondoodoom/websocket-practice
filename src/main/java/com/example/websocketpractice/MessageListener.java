package com.example.websocketpractice;

import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class MessageListener {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MessageListener.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
        log.info("===========");
        log.info("new web socket connection");
        log.info("===========");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        log.info("===========");
        log.info("disconnect");
        log.info("===========");
    }
}
