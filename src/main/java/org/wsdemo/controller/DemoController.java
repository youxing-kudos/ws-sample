package org.wsdemo.controller;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.websocket.Session;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wsdemo.socket.DemoEndpoint;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class DemoController {
    @PostMapping("/demo")
    public String demoPost(@RequestBody DemoPayload payload) throws IOException {
        if (Objects.isNull(payload.getUsername())) {
            return "'username' can not be null";
        }
        // notify by ws
        Session session = DemoEndpoint.getSession("user_001");
        if (session != null) {
            session.getBasicRemote().sendText(JSON.toJSONString(payload));
        }
        return "success";
    }

    @Data
    public static class DemoPayload {
        private String username;
        private Float score;
    }
}
