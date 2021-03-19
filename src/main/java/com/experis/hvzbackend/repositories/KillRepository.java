package com.experis.hvzbackend.repositories;

import com.experis.hvzbackend.models.Kill;
import com.experis.hvzbackend.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KillRepository extends JpaRepository<Kill, Long> {
}
