package com.example.AssignmentManagement.repository;


import com.example.AssignmentManagement.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsernameAndPasswordAndRole(String username, String password, String role);
}
