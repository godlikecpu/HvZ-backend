package com.experis.hvzbackend.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_state")
    private GameState gameState;

    @Column(name = "north_west_latitude")
    private float northWestLatitude;

    @Column(name = "north_west_longitude")
    private float northWestLongitude;

    @Column(name = "south_east_latitude")
    private float southEastLatitude;

    @Column(name = "south_east_longitude")
    private float southEastLongitude;

    @OneToMany(mappedBy = "game")
    private Set<Mission> missions;

    @OneToMany(mappedBy = "game")
    private Set<Player> players;

    @OneToMany(mappedBy = "game")
    private Set<Squad> squads;

    @OneToMany(mappedBy = "game")
    private List<Chat> chats;

    @OneToMany(mappedBy = "game")
    private Set<SquadMember> squadMembers;

    @OneToMany(mappedBy = "game")
    private Set<SquadCheckIn> squadCheckIns;

    @OneToMany(mappedBy = "game")
    private Set<Kill> kills;

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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public float getNorthWestLatitude() {
        return northWestLatitude;
    }

    public void setNorthWestLatitude(float northWestLatitude) {
        this.northWestLatitude = northWestLatitude;
    }

    public float getNorthWestLongitude() {
        return northWestLongitude;
    }

    public void setNorthWestLongitude(float northWestLongitude) {
        this.northWestLongitude = northWestLongitude;
    }

    public float getSouthEastLatitude() {
        return southEastLatitude;
    }

    public void setSouthEastLatitude(float southEastLatitude) {
        this.southEastLatitude = southEastLatitude;
    }

    public float getSouthEastLongitude() {
        return southEastLongitude;
    }

    public void setSouthEastLongitude(float southEastLongitude) {
        this.southEastLongitude = southEastLongitude;
    }

    public Set<Mission> getMissions() {
        return missions;
    }

    public void setMissions(Set<Mission> missions) {
        this.missions = missions;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Squad> getSquads() {
        return squads;
    }

    public void setSquads(Set<Squad> squads) {
        this.squads = squads;
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

    public Set<Kill> getKills() {
        return kills;
    }

    public void setKills(Set<Kill> kills) {
        this.kills = kills;
    }
}
