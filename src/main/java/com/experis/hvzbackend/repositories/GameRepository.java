package com.experis.hvzbackend.repositories;

import com.experis.hvzbackend.models.Game;
import com.experis.hvzbackend.models.Kill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
