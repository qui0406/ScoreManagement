//package com.scm.controllers;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.corundumstudio.socketio.annotation.OnEvent;
//import com.scm.pojo.User;
//import com.scm.pojo.WebSocketSession;
//import com.scm.services.UserService;
//import com.scm.services.WebSocketSessionService;
//import com.scm.utils.JwtUtils;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class SocketHandler {
//    SocketIOServer socketIOServer;
//    WebSocketSessionService webSocketSessionService;
//    UserService userService;
//
//    @OnConnect
//    public void clientConnected(SocketIOClient socketIOClient) throws Exception {
//        String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
//
//        String username = JwtUtils.validateTokenAndGetUsername(token);
//        if (username == null) {
//            log.error("Invalid token");
//            socketIOClient.disconnect();
//        } else {
//            User u = userService.getUserByUsername(username);
//
//            WebSocketSession webSocketSession = new WebSocketSession();
//            webSocketSession.setWebSocketSessionId(socketIOClient.getSessionId().toString());
//            webSocketSession.setTimestamp(LocalDateTime.now());
//            webSocketSession.setUserId(u.getId().toString());
//            webSocketSessionService.create(webSocketSession);
//            socketIOClient.set("username", username);
//        }
//    }
//
//    @OnDisconnect
//    public void clientDisconnected(SocketIOClient socketIOClient){
//        webSocketSessionService.delete(socketIOClient.getSessionId().toString());
//    }
//
//    @PostConstruct
//    public void startServer() {
//        socketIOServer.start();
//        socketIOServer.addListeners(this);
//        log.info("Server started");
//    }
//
//    @PreDestroy
//    public void stopServer() {
//        socketIOServer.stop();
//        log.info("Server stopped");
//    }
//
//}
