package com.experis.hvzbackend.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "squad_members")
public class SquadMember {

    public SquadMember(Rank rank, Game game, Squad squad, Player player) {
        this.rank = rank;
        this.game = game;
        this.squad = squad;
        this.player = player;
    }

    public SquadMember() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rank")
    private Rank rank;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "squadMember")
    Set<SquadCheckIn> squadCheckIns;


    @JsonGetter("game")
    public String getJsonGame() {
        if (game != null)
            return "/api/v1/game/" + game.getId();
        return null;
    }

    @JsonGetter("squad")
    public String getJsonSquad() {
        if (squad != null)
            return "/api/v1/game/" + game.getId() + "/squad/" + squad.getId();
        return null;
    }

    @JsonGetter("player")
    public String getJsonPlayer() {
        if (player != null)
            return "/api/v1/game/" + game.getId() + "/player/" + player.getId();
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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<SquadCheckIn> getSquadCheckIns() {
        return squadCheckIns;
    }

    public void setSquadCheckIns(Set<SquadCheckIn> squadCheckIns) {
        this.squadCheckIns = squadCheckIns;
    }
}
