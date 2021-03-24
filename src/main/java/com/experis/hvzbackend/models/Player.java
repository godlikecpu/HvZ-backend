package com.experis.hvzbackend.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_human")
    private boolean isHuman;

    @Column(name = "is_patient_zero")
    private boolean isPatientZero;

    @Column(name = "bite_code")
    private String biteCode;

    @OneToOne(mappedBy = "player")
    private User user;

    @OneToMany(mappedBy = "killer")
    private Set<Kill> kills;

    @OneToMany(mappedBy = "player")
    private List<Chat> chats;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @JsonGetter("game")
    public String getJsonGame() {
        if (game != null)
            return "/api/v1/game/" + game.getId();
        return null;
    }

    @JsonGetter("kills")
    public Set<String> getJsonKills() {
        if (kills != null)
            return kills.stream().map(kill -> "/api/v1/game/" + game.getId() + "/kill/" + kill.getId()).collect(Collectors.toSet());
        return null;
    }

    @JsonGetter("chats")
    public List<String> getJsonChats() {
        if (chats != null)
            return chats.stream().map(chat -> "/api/v1/game/" + game.getId() + "/chat/" + chat.getId()).collect(Collectors.toList());
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public boolean isPatientZero() {
        return isPatientZero;
    }

    public void setPatientZero(boolean patientZero) {
        isPatientZero = patientZero;
    }

    public String getBiteCode() {
        return biteCode;
    }

    public void setBiteCode(String biteCode) {
        this.biteCode = biteCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Kill> getKills() {
        return kills;
    }

    public void setKills(Set<Kill> kills) {
        this.kills = kills;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
