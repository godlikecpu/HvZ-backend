package com.experis.hvzbackend.controllers;


import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Mission;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.repositories.MissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/game/{game_id}/mission")
public class MissionController {
    private final MissionRepository missionRepository;
    private final GameController gameController;

    public MissionController(MissionRepository missionRepository, GameController gameController) {
        this.missionRepository = missionRepository;
        this.gameController = gameController;
    }

    @GetMapping
    public ResponseEntity<List<Mission>> getAllMission(@PathVariable Long game_id, @RequestBody Player player) {
        HttpStatus status;
        ResponseEntity<Game> game = gameController.getGame(game_id);
        Set<Mission> allMissions = game.getBody().getMissions();
        List<Mission> factionMissions = new ArrayList();
        for(Mission mission : allMissions) {
            if(mission.isHumanVisible() == player.isHuman()){
                factionMissions.add(mission);
            }
        }
        if (factionMissions.size() == 0){
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(factionMissions, status);

    }

    @GetMapping("/{mission_id}")
    public ResponseEntity<Mission> getMission(@PathVariable Long game_id, @PathVariable Long mission_id, @RequestBody Player player) {
        HttpStatus status;
        ResponseEntity<Game> game = gameController.getGame(game_id);
        Set<Mission> allMissions = game.getBody().getMissions();
        Mission targetMission = null;
        for (Mission mission : allMissions) {
            if (mission.getId() == mission_id) {
                targetMission = mission;
                break;
            }
        }
        if (targetMission == null) {
            status = HttpStatus.NO_CONTENT;
        } else if(targetMission != null && (player.isHuman() == targetMission.isHumanVisible())){
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(targetMission,  status);

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
