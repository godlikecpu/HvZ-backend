package com.experis.hvzbackend.controllers;


import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.GameState;
import com.experis.hvzbackend.repositories.GameRepository;
//import com.experis.hvzbackend.repositories.GameStatusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/game/{game_id}/state")
public class GameStatusController {
    // private final GameStatusRepository gameStatusRepository;
    private final GameRepository gameRepository;

    public GameStatusController(GameRepository gameRepository){/*GameStatusRepository gameStatusRepository,*/
    //    this.gameStatusRepository = gameStatusRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public ResponseEntity<GameState> getState(@PathVariable Long game_id){
        HttpStatus status;
        GameState returnState;
        Game game;
        if (gameRepository.existsById(game_id)) {
            game = gameRepository.findById(game_id).get();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(null, status);
        }
        returnState = game.getGameState();
        return new ResponseEntity<>(returnState, status);
    }

    @PutMapping
    public ResponseEntity<Game> updateState(@PathVariable Long game_id, GameState gameState) {
        HttpStatus status;
        Game game;
        if(!gameRepository.existsById(game_id)){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity(null, status);
        }
        game = gameRepository.findById(game_id).get();
        game.setGameState(gameState);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity(game, status);


    }
}
