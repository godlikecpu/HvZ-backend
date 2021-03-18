package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.models.Squad;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players/")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;


    public PlayerController(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/game/{game_id}/player")
    public ResponseEntity<List<Player>> getAllPlayers(@PathVariable Long game_id) {
        HttpStatus status;
        // find all players in a given game
        // i give you the id for a game
        //
        Game game = gameRepository.findById(game_id).get();
        List<Player> players = game.getPlayers();
        if(players.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(players, status);
    }

    @GetMapping("/game/{game_id}/player/{player_id}")
    public ResponseEntity<Squad> getPlayer(@PathVariable Long game_id, @PathVariable Long player_id) {
        HttpStatus status;

    }

    @PostMapping("/game/{game_id}/player")
    public ResponseEntity<Player> addPlayer(@PathVariable Long game_id, @RequestBody Player player) {

    }

    @PutMapping("/game/{game_id}/player/{player_id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {

    }

    @DeleteMapping("/game/{game_id}/player/{player_id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long game_id, @PathVariable Long player_id ) {

    }
}
