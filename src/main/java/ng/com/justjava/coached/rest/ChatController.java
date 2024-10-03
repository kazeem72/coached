package ng.com.justjava.coached.rest;

import ng.com.justjava.coached.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Controller class for handling chat-related functionality.
 */
@Controller
public class ChatController {

    /**
     * Registers a user for chat.
     * 
     * param chatMessage The chat message containing the sender's information.
     * param headerAccessor The SimpMessageHeaderAccessor object used to access session attributes.
     * return The registered chat message.
     */
    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println(" chatMessage.getSender() =="+chatMessage.getSender());
        chatMessage.setContent(chatMessage.getContent());
        return chatMessage;
    }

    /**
     * Sends a chat message to all connected users.
     * 
     * param chatMessage The chat message to be sent.
     * return The sent chat message.
     */
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {

        //chatMessage.setSender("coach-0001");
        System.out.println("Sender====="+chatMessage.getSender());
        System.out.println("content====="+chatMessage.getContent());
        System.out.println("type====="+chatMessage.getType());
        return chatMessage;
    }
}
