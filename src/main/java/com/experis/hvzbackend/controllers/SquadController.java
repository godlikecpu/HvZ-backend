package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.models.Squad;
import com.experis.hvzbackend.repositories.SquadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/squads")
public class SquadController {
    private final SquadRepository squadRepository;
    private final GameController gameController;

    public SquadController(SquadRepository squadRepository, GameController gameController) {
        this.squadRepository = squadRepository;
        this.gameController = gameController;
    }

    @GetMapping("/game/{game_id}/squad")
    public ResponseEntity<List<Squad>> getAllSquads(@PathVariable Long game_id) {
        HttpStatus status;
        List<Squad> squads = squadRepository.findAll();
        if(squads.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(squads, status);
    }

    @GetMapping("/game/{game_id}/{squad_id}")
    public ResponseEntity<Squad> getSquad(@PathVariable Long game_id, @PathVariable Long squad_id) {
        HttpStatus status;
        Game game = gameController.getGame(game_id);
        Squad squad = game.getSquad(squad_id);
        if(squad != null) {
            status = HttpStatus.OK;
            return new ResponseEntity<>(squad, status);
        } else {
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(null, status);
        }
    }

    @PostMapping("/game/{game_id}/squad")
    public ResponseEntity<Squad> addSquad(@PathVariable Long game_id, @RequestBody Player player) {
        Squad newSquad = new Squad(String name, )
        HttpStatus status;

    }

    @PostMapping("/game/{game_id}/squad/{squad_id}")
    public ResponseEntity<Squad> joinSquad(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Long player_id) {
        HttpStatus status;

        Game game = gameController.getGame(game_id);
        Squad squad = game.getSquad(squad_id);

        if(squad != null) {
            squad.addMember(player_id);
            squadRepository.save(squad);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(squad, status);
    }

}
