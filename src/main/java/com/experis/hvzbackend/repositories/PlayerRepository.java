package com.experis.hvzbackend.repositories;

import com.experis.hvzbackend.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllById(Long game_id);
}
