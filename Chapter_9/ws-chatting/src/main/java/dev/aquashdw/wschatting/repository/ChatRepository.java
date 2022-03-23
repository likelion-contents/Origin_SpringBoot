package dev.aquashdw.wschatting.repository;

import dev.aquashdw.wschatting.model.ChatRoom;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ChatRepository {
    private List<ChatRoom> chatRooms;

    public ChatRepository() {
        this.chatRooms = new ArrayList<>();
        this.chatRooms.add(new ChatRoom("general", "general"));
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public ChatRoom createChatRoom(String roomName) {
        ChatRoom newRoom = new ChatRoom(UUID.randomUUID().toString(), roomName);
        this.chatRooms.add(newRoom);
        return newRoom;
    }

    public ChatRoom findRoomById(String roomId) {
        for (ChatRoom chatRoom: chatRooms){
            if (chatRoom.getRoomId().equals(roomId))
                return chatRoom;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
