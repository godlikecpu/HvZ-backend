package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.*;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.PlayerRepository;
import com.experis.hvzbackend.repositories.SquadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/game/{game_id}/squad")
public class SquadController {
    private final SquadRepository squadRepository;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;


    public SquadController(SquadRepository squadRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.squadRepository = squadRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Squad>> getAllSquads(@PathVariable Long game_id) {
        HttpStatus status;
        List<Squad> allSquads = squadRepository.findAll();


        if(allSquads.size() == 0) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(allSquads, status);
    }

    @GetMapping("/{squad_id}")
    public ResponseEntity<Squad> getSquad(@PathVariable Long game_id, @PathVariable Long squad_id) {
        HttpStatus status;

        Squad squad = squadRepository.findById(squad_id).get();

        if(squad != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NO_CONTENT;
        }

        status = HttpStatus.OK;
        return new ResponseEntity<>(squad, status);
    }

    @PostMapping()
    public ResponseEntity<Squad> addSquad(@PathVariable Long game_id, @RequestBody Player player, @RequestBody String squadName) {
        //Creates a squad object. Accepts appropriate parameters in the request body as
        //application/json. Should also automatically create a corresponding squad member
        //object that registers the player as the ranking member of the squad they just created.

        //Rank = LEADER

        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();

        SquadMember leader = new SquadMember(Rank.LEADER, game, )
        Squad newSquad = new Squad(squadName, true, game, )

    }

    @PostMapping("/{squad_id}/join")
    public ResponseEntity<Squad> joinSquad(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player) {
        HttpStatus status;

        //Rank = Member

        Squad squad = squadRepository.findById(squad_id).get();
        Set<SquadMember> members = squad.getSquadMembers();

        if(members.size() == 0) {
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(null, status);
        }

        Game game = gameRepository.findById(game_id).get();
        Player squadPlayer = playerRepository.findById(player.getId()).get();

        members.add(new SquadMember(Rank.MEMBER, game, squad, squadPlayer));

        squad.setSquadMembers(members);

        squadRepository.save(squad);
        status = HttpStatus.OK;

        return new ResponseEntity<>(squad, status);
    }

    //Commented out as this endpoint is not a part of the current scope (first sprint)
    /*
    @PutMapping("/{squad_id}")
    public ResponseEntity<> updateSquadObjective(@PathVariable Long game_id, @PathVariable Long squad_id) {
        //Updates a squad object. Accepts appropriate parameters in the request body as
        //application/json. Admin only.
    }
    */

    @DeleteMapping("/{squad_id}")
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

    @GetMapping("/{squad_id}/chat")
    public ResponseEntity<Set<Chat>> getChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player){
        //Returns a list of chat messages. Optionally accepts appropriate query parameters.
        //The messages returned should take into account the current game state of the player,
        //i.e. a human should recieve chat messages addressed to the ”global” (cross-faction chat)
        //and ”human” chats but not the ”zombie” chat.

        HttpStatus status = HttpStatus.NO_CONTENT;

        //Only humans allowed this information
        if(!player.isHuman()) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        }

        Squad squad = squadRepository.findById(squad_id).get();
        Set<Chat> squadChat = squad.getChats();

        return new ResponseEntity<>(squadChat, HttpStatus.OK);


    }

    @PostMapping("/{squad_id}/chat")
    public ResponseEntity<Set<Chat>> sendSquadChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player, @RequestBody Chat chat) {
        //Send a new chat message addressed to a particular squad. Accepts appropriate parameters in the request body as application/json. Only administrators and members
        //of a squad who are still in the appropriate faction may send messages to the squad chat,
        //i.e. a human who has died should not be able to continue sending messages to their
        //human squad; in this event it returns 403 Forbidden.

        HttpStatus status;

        //Only humans allowed this information
        if(!player.isHuman()) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        }

        Squad squad = squadRepository.findById(squad_id).get();

        Set<Chat> squadChat = squad.getChats();
        squadChat.add(chat);
        squad.setChats(squadChat);

        squadRepository.save(squad);
        status = HttpStatus.OK;

        return new ResponseEntity<>(squadChat, status);
    }

    @GetMapping("/{squad_id}/check-in")
    public ResponseEntity<Set<SquadCheckIn>> getSquadCheckIns(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player) {
        //Get a list of squad check-in markers. Optionally accepts appropriate query parameters.
        //Only administrators and members of a squad who are still in the appropriate faction
        //may see squad check-ins, i.e. a human who has died should not be able to access the
        //check-ins of their human squad; in this event it returns 403 Forbidden

        HttpStatus status;

        Squad squad = squadRepository.findById(squad_id).get();
        Set<SquadCheckIn> squadCheckIns = null;

        if(squad != null) {
            squadCheckIns = squad.getSquadCheckIns();
        }

        //Only humans are allowed this information
        if(!player.isHuman()) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        } else {
            status = HttpStatus.OK;
            return new ResponseEntity<>(squadCheckIns, status);
        }
    }

    @PostMapping("/{squad_id}/check-in")
    public ResponseEntity<SquadCheckIn> createCheckIn(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody SquadCheckIn checkIn, @RequestBody Player player) {
        //Create a squad checkin. Accepts appropriate parameters in the request body as
        //application/json. Only members of a squad who are still in the appropriate faction may check-in with their squad, i.e. a human who has died should not be able to
        //access the check-ins of their human squad; in this event it returns 403 Forbidden.

        HttpStatus status;

        //Only humans allowed to access this information
        if(!player.isHuman()) {
            status = HttpStatus.FORBIDDEN;
            return new ResponseEntity<>(null, status);
        }

        Squad squad = squadRepository.findById(squad_id).get();

        Set<SquadCheckIn> squadCheckIns = squad.getSquadCheckIns();
        squadCheckIns.add(checkIn);
        squad.setSquadCheckIns(squadCheckIns);

        squadRepository.save(squad);
        status = HttpStatus.OK;
        return new ResponseEntity<>(checkIn, status);

    }

}