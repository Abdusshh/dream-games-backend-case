package com.project.dreamgamesbackendcase.repository;

import com.project.dreamgamesbackendcase.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    @Query("SELECT t FROM Team t WHERE t.memberCount < t.capacity")
    List<Team> findTeamsWithAvailableSpots();
}
