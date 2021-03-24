package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/game/{game_id}/player")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public PlayerController(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping()
    public ResponseEntity<Set<Player>> getAllPlayers(@PathVariable Long game_id) {
        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();
        Set<Player> players = game.getPlayers();
        if(players.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(players, status);
    }

    @GetMapping("/{player_id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long game_id, @PathVariable Long player_id) {
        HttpStatus status;
        Player player = playerRepository.findById(player_id).get();
        if(player == null) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(player, status);
    }

    @PostMapping()
    public ResponseEntity<Player> addPlayer(@PathVariable Long game_id, @RequestBody Player player) {
        HttpStatus status;
        Player returnPlayer = playerRepository.save(player);
        status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnPlayer, status);
    }

    @PutMapping("/{player_id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        HttpStatus status;
        Player returnPlayer = null;
        if(playerRepository.existsById(player.getId())){
            status = HttpStatus.NO_CONTENT;
            returnPlayer = playerRepository.save(player);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnPlayer, status);
    }

    @DeleteMapping("/{player_id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long game_id, @PathVariable Long player_id ) {
        HttpStatus status;
        if(playerRepository.existsById(player_id)){
            playerRepository.deleteById(player_id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }
}
