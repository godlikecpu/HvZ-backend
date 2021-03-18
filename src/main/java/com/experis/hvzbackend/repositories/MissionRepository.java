package com.experis.hvzbackend.repositories;


import com.experis.hvzbackend.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
