package com.example.AssignmentManagement.repository;

import com.example.AssignmentManagement.model.Submissions;
import com.example.AssignmentManagement.response.SubmittedAssignmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submissions, Long> {

}
