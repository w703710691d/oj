package com.swustacm.poweroj.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.security.Principal;


/**
 * @author  xingzi
 */
@Component

public class WebSocketDecoratorFactory implements WebSocketHandlerDecoratorFactory {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketDecoratorFactory.class);
    @Override
    public  WebSocketHandler decorate( WebSocketHandler webSocketHandler) {
        return new WebSocketHandlerDecorator(webSocketHandler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                logger.info("有人连接啦  sessionId = {}", session.getId());
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    logger.info("key = {} 存入", principal.getName());
                    // 身份校验成功，缓存socket连接
                    WebSocketManager.add(principal.getName(), session);
                }
                super.afterConnectionEstablished(session);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                logger.info("有人退出连接啦  sessionId = {}", session.getId());
                Principal principal = session.getPrincipal();
                if (principal != null) {
                    // 身份校验成功，移除socket连接
                    WebSocketManager.remove(principal.getName());
                }
                super.afterConnectionClosed(session, closeStatus);
            }
        };
    }
}
