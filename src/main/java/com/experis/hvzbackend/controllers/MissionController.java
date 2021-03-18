package com.experis.hvzbackend.controllers;


import com.experis.hvzbackend.repositories.GameRepository;
import com.experis.hvzbackend.repositories.MissionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/game")
public class MissionController {
    private final MissionRepository missionRepository;
    private final GameRepository gameRepository;

    public MissionController(MissionRepository missionRepository, GameRepository gameRepository) {
        this.missionRepository = missionRepository;
        this.gameRepository = gameRepository;
    }




}
