package com.experis.hvzbackend.controllers;


import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Mission;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.MissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/game/{game_id}/mission")
public class MissionController {
    private final MissionRepository missionRepository;
    private final GameRepository gameRepository;

    public MissionController(MissionRepository missionRepository, GameRepository gameRepository) {
        this.missionRepository = missionRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public ResponseEntity<Set<Mission>> getAllMission(@PathVariable Long game_id, @RequestBody Player player) {
        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();
        Set<Mission> allMissions = game.getMissions();
        Set<Mission> zombieMissions = new HashSet<>();
        Set<Mission> humanMissions = new HashSet<>();
        if(allMissions.size() == 0) { return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); }
        for (Mission mission : allMissions) {
            if(mission.isHumanVisible()){
                humanMissions.add(mission);
            } else {
                zombieMissions.add(mission);
            }
        }
        boolean playerStatus = player.isHuman();
        if (playerStatus){
            status = HttpStatus.OK;
            return new ResponseEntity<>(humanMissions, status);
        } else {
            status = HttpStatus.OK;
            return new ResponseEntity<>(zombieMissions, status);
        }
    }

    @GetMapping("/{mission_id}")
    public ResponseEntity<Mission> getMission(@PathVariable Long game_id, @PathVariable Long mission_id, @RequestBody Player player) {
        HttpStatus status;
        Mission mission = missionRepository.findById(mission_id).get();
        if (mission == null) {
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(null, status); // we return so status is not changed
        } else if(player.isHuman() && mission.isHumanVisible()){
            status = HttpStatus.OK;
        } else if(!player.isHuman() && mission.isZombieVisible()){
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        }
        return new ResponseEntity<>(mission, status);
    }

    @PostMapping
    public ResponseEntity<Mission> addMission (@RequestBody Mission mission) {
        HttpStatus status = HttpStatus.CREATED;
        Mission returnMission = missionRepository.save(mission);
        return new ResponseEntity<>(returnMission, status);
    }

    @PutMapping("/{mission_id}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long game_id, @RequestBody Mission mission) {
        HttpStatus status;
        Mission returnMission = null;
        if(missionRepository.existsById(mission.getId())){
            status = HttpStatus.NO_CONTENT;
            returnMission = missionRepository.save(mission);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMission, status);
    }

    @DeleteMapping("/{mission_id}")
    public ResponseEntity<Mission> deleteMission(@PathVariable Long mission_id) {
        HttpStatus status;
        if(missionRepository.existsById(mission_id)){
            missionRepository.deleteById(mission_id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(null, status);
    }
}
