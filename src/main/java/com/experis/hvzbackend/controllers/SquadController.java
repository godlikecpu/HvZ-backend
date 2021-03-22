package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.*;
import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.PlayerRepository;
import com.experis.hvzbackend.repositories.SquadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<Set<Squad>> getAllSquads(@PathVariable Long game_id) {
        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();
        Set<Squad> allSquads = game.getSquads();
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
        return new ResponseEntity<>(squad, status);
    }

    @PostMapping()
    public ResponseEntity<Squad> addSquad(@PathVariable Long game_id, @RequestBody Player player, @RequestBody String squadName) {
        HttpStatus status;
        Game game = gameRepository.findById(game_id).get();

        Squad newSquad = new Squad(squadName, true, game, new HashSet<SquadMember>());

        //Saving squad with no members to get an autogenerated ID
        squadRepository.save(newSquad);

        //Getting newly generated squad with ID
        List<Squad> allSquads = squadRepository.findAll();

        for (Squad squad: allSquads) {
            if(squad.getName() == newSquad.getName()) {
                newSquad = squad;
                break;
            }
        }

        if(((Long)newSquad.getId()) ==  null) {
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(null, status);
        }

        //Creating a new member
        SquadMember member = new SquadMember(Rank.LEADER, game, newSquad, player);

        //Getting list of members in squad
        Set<SquadMember> members = newSquad.getSquadMembers();
        members.add(member);

        //updating list of members
        newSquad.setSquadMembers(members);

        //updating entire squad
        squadRepository.save(newSquad);

        status = HttpStatus.OK;
        return new ResponseEntity<>(newSquad, status);
    }

    @PostMapping("/{squad_id}/join")
    public ResponseEntity<Squad> joinSquad(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player) {
        HttpStatus status;

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

    @PutMapping("/{squad_id}")
    public ResponseEntity<Squad> updateSquadObjective(@PathVariable Long game_id, @PathVariable Long squad_id,
                                                 @RequestBody Squad squad) {
        // Admin only.
        HttpStatus status;
        Squad currentSquad = squadRepository.findById(squad_id).get();
        if(currentSquad.getId() != squad_id){
            status = HttpStatus.NO_CONTENT;
            return new ResponseEntity<>(null, status);
        }
        squadRepository.save(squad);
        status = HttpStatus.OK;
        return new ResponseEntity<>(squad, status);
    }


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
    public ResponseEntity<List<Chat>> getChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player){
        //Returns a list of chat messages. Optionally accepts appropriate query parameters.
        //The messages returned should take into account the current game state of the player,
        //i.e. a human should recieve chat messages addressed to the ”global” (cross-faction chat)
        //and ”human” chats but not the ”zombie” chat.

        HttpStatus status;
        Squad squad = squadRepository.findById(squad_id).get();
        Set<SquadMember> squadMembers = squad.getSquadMembers();
        boolean isMember = false;
        for (SquadMember squadMember: squadMembers) {
            if(squadMember.getPlayer().getId() == player.getId()){
                isMember = true;
                break;
            }
        }

        List<Chat> chats = squad.getChats();
        List<Chat> humanChat = new ArrayList<>();
        List<Chat> zombieChat = new ArrayList<>();
        for (Chat chat:chats) {
            if(chat.isHumanGlobal()){
                humanChat.add(chat);
            }
            if(chat.isZombieGlobal()){
                zombieChat.add(chat);
            }
        }
        if(isMember){
            if(player.isHuman() && squad.isHuman()){
                status = HttpStatus.OK;
                return new ResponseEntity<>(humanChat, status);
            } else if(!player.isHuman() && !squad.isHuman()){
                status = HttpStatus.OK;
                return new ResponseEntity<>(zombieChat, status);
            }
        }
        status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(null, status);
    }

    @PostMapping("/{squad_id}/chat")
    public ResponseEntity<List<Chat>> sendSquadChat(@PathVariable Long game_id, @PathVariable Long squad_id, @RequestBody Player player, @RequestBody Chat chat) {
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

        List<Chat> squadChat = squad.getChats();
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
