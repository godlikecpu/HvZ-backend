package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Kill;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.KillRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/game/{game_id}/kill")
public class KillController {

    private final GameRepository gameRepository;
    private final KillRepository killRepository;

    public KillController(GameRepository gameRepository, KillRepository killRepository) {
        this.gameRepository = gameRepository;
        this.killRepository = killRepository;
    }

    @GetMapping()
    public ResponseEntity<Set<Kill>> getAllKills(@PathVariable Long game_id) {
        HttpStatus status;
        Game game = gameRepository.findById(kill_id).get();
        Set<Kill> kills = game.getKills();
        if(kills.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(kills, status);
    }

    @GetMapping("/{kill_id}")
    public ResponseEntity<Kill> getKill(@PathVariable Long game_id, @PathVariable Long kill_id) {
        HttpStatus status;
        Kill returnKill = null;
        Game game = gameRepository.findById(kill_id).get();
        Set<Kill> kills = game.getKills();
        for (Kill kill : kills) {
            if(kill.getId() == kill_id){
                returnKill = kill;
            }
        }
        if(returnKill == null) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(returnKill, status);
    }

    @PostMapping()
    public ResponseEntity<Kill> addKill(@RequestBody Kill kill) {
        HttpStatus status;
        Kill returnKill = killRepository.save(kill);
        status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnKill, status);
    }

    @PutMapping("/{kill_id}")
    public ResponseEntity<Kill> updateKill(@RequestBody Kill kill) {
        HttpStatus status;
        Kill returnKill = null;
        if(killRepository.existsById(kill.getId())){
            status = HttpStatus.NO_CONTENT;
            returnKill = killRepository.save(kill);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnKill, status);
    }

    @DeleteMapping("/{kill_id}")
    public ResponseEntity<Kill> deleteKill(@PathVariable Long game_id, @PathVariable Long kill_id ) {
        HttpStatus status;
        if(killRepository.existsById(kill_id)){
            killRepository.deleteById(kill_id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(null, status);
    }
}
