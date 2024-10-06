package com.example.AssignmentManagement.repository;

import com.example.AssignmentManagement.model.Assignments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignments,Integer> {
    List<Assignments> findAll();

    Optional<Assignments> findByTitle(String name);
}
