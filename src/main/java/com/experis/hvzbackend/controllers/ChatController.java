package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Chat;
import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.repositories.ChatRepository;
import com.experis.hvzbackend.repositories.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/{game_id}")
public class ChatController {

    private final GameRepository gameRepository;
    private final ChatRepository chatRepository;

    public ChatController(GameRepository gameRepository, ChatRepository chatRepository){
        this.gameRepository = gameRepository;
        this.chatRepository = chatRepository;
    }

    @GetMapping("/chat")
    public ResponseEntity<Set<Chat>> getAllChats(@PathVariable Long game_id) {
        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();
        Set<Chat> chats = game.getChats();
        if(chats.size() == 0){
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(chats, status);
    }

    @PostMapping("/chat")
    public ResponseEntity<Chat> sendChat(@RequestBody Chat chat) {
        HttpStatus status = HttpStatus.CREATED;
        Chat returnChat = chatRepository.save(chat);
        return new ResponseEntity<>(returnChat, status);
    }
}
