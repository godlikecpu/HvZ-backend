package com.experis.hvzbackend.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
        isHuman = human;
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
