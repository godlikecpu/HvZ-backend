package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Chat;
import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.repositories.ChatRepository;
import com.experis.hvzbackend.repositories.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/game")
public class GameController {
    private final GameRepository gameRepository;
    private final ChatRepository chatRepository;

    public GameController(GameRepository gameRepository, ChatRepository chatRepository){
        this.gameRepository = gameRepository;
        this.chatRepository = chatRepository;
    }


    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        HttpStatus status;
        List<Game> games = gameRepository.findAll();
        if(games.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(games, status);
    }

    @GetMapping("/{game_id}")
    public ResponseEntity<Game> getGame(@PathVariable Long game_id) {
        HttpStatus status;
        Game returnGame;
        if (gameRepository.existsById(game_id)){
            status = HttpStatus.OK;
            returnGame = gameRepository.findById(game_id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
            returnGame = null;
        }
        return new ResponseEntity<>(returnGame, status);
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game returnGame = gameRepository.save(game);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnGame, status);
    }

    @PutMapping("/{game_id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long game_id, @RequestBody Game game) {
        Game returnGame;
        HttpStatus status;
        if(!game_id.equals(game.getId())){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(null, status);
        }
        returnGame = gameRepository.save(game);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnGame, status);
    }

    @DeleteMapping("/{game_id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long game_id){
        if(gameRepository.existsById(game_id)){
           Game game = gameRepository.findById(game_id).get();
           Set<Player> players = game.getPlayers();
           for (Player player : players) {
               player.setGame(null);
           }
           gameRepository.delete(game);
           return new ResponseEntity<>(game, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
