package com.profilerenovation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profilerenovation.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    User findByEmail(String email);
}
