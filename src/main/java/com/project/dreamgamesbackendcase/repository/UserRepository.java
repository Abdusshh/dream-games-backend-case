package com.project.dreamgamesbackendcase.repository;

import com.project.dreamgamesbackendcase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
