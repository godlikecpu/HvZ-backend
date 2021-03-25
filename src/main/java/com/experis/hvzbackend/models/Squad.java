package com.experis.hvzbackend.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "squads")
public class Squad {

    public Squad(String name, boolean isHuman, Game game, Set<SquadMember> squadMembers) {
        this.name = name;
        this.isHuman = isHuman;
        this.game = game;
        this.squadMembers = squadMembers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "is_human")
    private boolean isHuman;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "squad")
    private List<Chat> chats;

    @OneToMany(mappedBy = "squad")
    private Set<SquadMember> squadMembers;

    @OneToMany(mappedBy = "squad")
    private Set<SquadCheckIn> squadCheckIns;

    @JsonGetter("game")
    public String getJsonGame() {
        if (game != null)
            return "/api/v1/game/" + game.getId();
        return null;
    }

    @JsonGetter("chats")
    public List<String> getJsonChats() {
        if (chats != null)
            return chats.stream().map(chat -> "/api/v1/game/" + game.getId() + "/chat/" + chat.getId()).collect(Collectors.toList());
        return null;
    }

    @JsonGetter("squadMembers")
    public Set<String> getJsonSquadMembers() {
        if (squadMembers != null)
            return squadMembers.stream().map(squadMember -> "/api/v1/game/" + game.getId() + "/player/" + squadMember.getPlayer().getId()).collect(Collectors.toSet());
        return null;
    }

    @JsonGetter("squadCheckIns")
    public Set<String> getJsonSquadCheckIns() {
        if (squadCheckIns != null)
            return squadCheckIns.stream().map(sci -> "/api/v1/game/" + game.getId() + "/squad/" + sci.getSquad().getId() + "/check-in/" + sci.getId()).collect(Collectors.toSet());
        return null;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        this.isHuman = human;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public Set<SquadMember> getSquadMembers() {
        return squadMembers;
    }

    public void setSquadMembers(Set<SquadMember> squadMembers) {
        this.squadMembers = squadMembers;
    }

    public Set<SquadCheckIn> getSquadCheckIns() {
        return squadCheckIns;
    }

    public void setSquadCheckIns(Set<SquadCheckIn> squadCheckIns) {
        this.squadCheckIns = squadCheckIns;
    }
}
