package com.studyplatform.server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Клієнт надсилає повідомлення на /app/chat
    @MessageMapping("/chat")
    // А всі підписники слухають /topic/messages
    @SendTo("/topic/messages")
    public String handleMessage(String message) {
        // поки що просто повертаємо те ж саме повідомлення
        return message;
    }
}
