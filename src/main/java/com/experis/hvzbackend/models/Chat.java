package com.experis.hvzbackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    @Column(name = "is_human_global")
    private boolean isHumanGlobal;

    @Column(name = "is_zombie_global")
    private boolean isZombieGlobal;

    @Column(name = "chat_time")
    private Date chatTime;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHumanGlobal() {
        return isHumanGlobal;
    }

    public void setHumanGlobal(boolean humanGlobal) {
        isHumanGlobal = humanGlobal;
    }

    public boolean isZombieGlobal() {
        return isZombieGlobal;
    }

    public void setZombieGlobal(boolean zombieGlobal) {
        isZombieGlobal = zombieGlobal;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }
}
