package com.experis.hvzbackend.repositories;

import com.experis.hvzbackend.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SquadRepository extends JpaRepository<Squad, Long> {
}
