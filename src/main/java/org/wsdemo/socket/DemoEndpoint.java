package org.wsdemo.socket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Component
@ServerEndpoint(value = "/demo/websocket")
public class DemoEndpoint {
    private boolean isConnected = false;

    private static Map<String, Session> sessionMap = new Hashtable<>();

    public static Session getSession(String userid) {
        return sessionMap.get(userid);
    }

    @OnOpen
    public void onOpen(Session session) throws IOException, InterruptedException {
        sessionMap.put("user_001", session);
        // Get session and WebSocket connection
        isConnected = true;
//        int count = 0;
//        do {
//            String random = (int) (Math.random() * 100000) + "";
//            String message = "timestamp: " + System.currentTimeMillis() + ", random: " + random;
////            System.out.println(">>>" + message);
//            session.getBasicRemote().sendText(message);
//            Thread.sleep(1000);
//            if (!isConnected) break;
//            if (count++ > 1000) break;
//        } while (session.isOpen());
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        session.getBasicRemote().sendText("your message: " + message);
        isConnected = true;
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
        isConnected = false;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        isConnected = false;
    }
}
