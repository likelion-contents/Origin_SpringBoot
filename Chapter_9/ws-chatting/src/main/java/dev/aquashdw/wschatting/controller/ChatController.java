package dev.aquashdw.wschatting.controller;

import dev.aquashdw.wschatting.model.ChatRoom;
import dev.aquashdw.wschatting.repository.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping("rooms")
    public @ResponseBody ResponseEntity<List<ChatRoom>> getChatRooms(){
        return ResponseEntity.ok(chatRepository.getChatRooms());
    }

    @PostMapping("rooms")
    public @ResponseBody ResponseEntity<ChatRoom> createRoom(@RequestParam("room-name") String roomName){
        return ResponseEntity.ok(chatRepository.createChatRoom(roomName));
    }

    @GetMapping("room/name")
    public @ResponseBody ResponseEntity<ChatRoom> getRoomName(@RequestParam("room-id") String roomId) {
        logger.info(roomId);
        return ResponseEntity.ok(chatRepository.findRoomById(roomId));
    }

    @GetMapping("{roomId}/{userId}")
    public String enterRoom(){
        return "/chat-room.html";
    }
}
