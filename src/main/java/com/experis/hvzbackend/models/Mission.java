package com.experis.hvzbackend.models;


import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "missions")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "is_human_visible")
    private boolean isHumanVisible;

    @Column(name = "is_zombie_visible")
    private boolean isZombieVisible;

    private String description;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    @JsonGetter("game")
    public String getJsonGame() {
        if (game != null)
            return "/api/v1/game/" + game.getId();
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

    public boolean isHumanVisible() {
        return isHumanVisible;
    }

    public void setHumanVisible(boolean humanVisible) {
        this.isHumanVisible = humanVisible;
    }

    public boolean isZombieVisible() {
        return isZombieVisible;
    }

    public void setZombieVisible(boolean zombieVisible) {
        this.isZombieVisible = zombieVisible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
