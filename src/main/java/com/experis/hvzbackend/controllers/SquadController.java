package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Player;
import com.experis.hvzbackend.models.Squad;
import com.experis.hvzbackend.repositories.SquadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
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

    @PostMapping("/game/{game_id}/squad/{squad_id}/join")
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

    @PutMapping("/game/{game_id}/squad/{squad_id}")
    public ResponseEntity<> updateSquadObjective(@PathVariable Long game_id, @PathVariable Long squad_id) {
        //Updates a squad object. Accepts appropriate parameters in the request body as
        //application/json. Admin only.
    }

    @DeleteMapping("/game/{game_id}/squad/{squad_id}")
    public ResponseEntity<Squad> deleteSquad(@PathVariable Long game_id, @PathVariable Long squad_id) {
        //Delete a squad. Admin only

        //might need a role check to see if caller is admin or not

        HttpStatus status;
        if(squadRepository.existsById(squad_id)) {
            squadRepository.deleteById(squad_id);
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(null, status);
    }

    @GetMapping("/game/{game_id}/squad/{squad_id}/chat")
    public ResponseEntity<List<Chat>> getChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player){
        //Returns a list of chat messages. Optionally accepts appropriate query parameters.
        //The messages returned should take into account the current game state of the player,
        //i.e. a human should recieve chat messages addressed to the ”global” (cross-faction chat)
        //and ”human” chats but not the ”zombie” chat.

        HttpStatus status = HttpStatus.NO_CONTENT;

        Chat chats = chatRepository.getAll();
        List<Chat> squadChat = new ArrayList();

        for(Chat chat: chats) {
            if(chat.squad_id == squad_id) {
                squadChat.add(chat);
            }
        }

        //Only humans squad members can get the chat
        if(player.is_human) {
            return new ResponseEntity<>(squadChat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/game/{game_id}/squad/{squad_id}/chat")
    public ResponseEntity<List<String>> sendSquadChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player, @RequestBody Chat chat) {
        //Send a new chat message addressed to a particular squad. Accepts appropriate parameters in the request body as application/json. Only administrators and members
        //of a squad who are still in the appropriate faction may send messages to the squad chat,
        //i.e. a human who has died should not be able to continue sending messages to their
        //human squad; in this event it returns 403 Forbidden.

        HttpStatus status;

        if(!player.is_human) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        } else
            chatRepository.save(chat);
            status = HttpStatus.OK;

            return new ResponseEntity<>(chat, status);
    }

    @GetMapping("/game/{game_id}/squad/{squad_id}/check-in")
    public ResponseEntity<List<SquadCheckIn>> squadCheckIns(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player) {
        //Get a list of squad check-in markers. Optionally accepts appropriate query parameters.
        //Only administrators and members of a squad who are still in the appropriate faction
        //may see squad check-ins, i.e. a human who has died should not be able to access the
        //check-ins of their human squad; in this event it returns 403 Forbidden

        HttpStatus status;

        SquadCheckIn allCheckIns = SquadCheckInRepository.findAll();
        List<SquadCheckIn> squadCheckIns = new ArrayList();

        for (SquadCheckIn checkIn: allCheckIns) {
            if(checkIn.squad_id == squad_id) {
                squadCheckIns.add(checkIn);
            }
        }

        if(!player.is_human) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        } else {
            status = HttpStatus.OK;
            return new ResponseEntity<>(squadCheckIns, status);
        }
    }

    @PostMapping("/game/{game_id}/squad/{squad_id}/check-in")
    public ResponseEntity<SquadCheckIn> createCheckIn(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody SquadCheckIn checkIn, @RequestBody Player player) {
        //Create a squad checkin. Accepts appropriate parameters in the request body as
        //application/json. Only members of a squad who are still in the appropriate faction may check-in with their squad, i.e. a human who has died should not be able to
        //access the check-ins of their human squad; in this event it returns 403 Forbidden.

        HttpStatus status;

        if(!player.is_human) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        }

        SquadCheckIn ckeckIn = squadCheckInRepository.save(checkIn);
        status = HttpStatus.OK;
        return new ResponseEntity<>(checkIn, status);

    }

}
