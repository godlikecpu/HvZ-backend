package com.experis.hvzbackend.controllers;

import com.experis.hvzbackend.models.Game;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    public Game getGame(Long game_id) {
        return new Game();
    }
}
