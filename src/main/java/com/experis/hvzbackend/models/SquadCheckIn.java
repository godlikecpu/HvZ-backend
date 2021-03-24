package com.experis.hvzbackend.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "squad_check_ins")
public class SquadCheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private float latitude;

    private float longitude;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @ManyToOne
    @JoinColumn(name = "squad_member_id")
    private SquadMember squadMember;

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

    @JsonGetter("squadMember")
    public String getJsonSquadMember() {
        if (squadMember != null)
            return "/api/v1/game/" + game.getId() + "/player/" + squadMember.getPlayer().getId();
        return null;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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

    public SquadMember getSquadMember() {
        return squadMember;
    }

    public void setSquadMember(SquadMember squadMember) {
        this.squadMember = squadMember;
    }
}
