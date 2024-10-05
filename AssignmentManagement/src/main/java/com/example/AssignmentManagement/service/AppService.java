package com.example.AssignmentManagement.service;

import com.example.AssignmentManagement.exception.UserException;
import com.example.AssignmentManagement.model.Assignments;
import com.example.AssignmentManagement.model.Submissions;
import com.example.AssignmentManagement.model.Users;
import com.example.AssignmentManagement.repository.AssignmentRepository;
import com.example.AssignmentManagement.repository.SubmissionRepository;
import com.example.AssignmentManagement.repository.UserRepository;
import com.example.AssignmentManagement.request.AssignmentRequest;
import com.example.AssignmentManagement.request.UserRequest;
import com.example.AssignmentManagement.response.BaseResponse;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public SubmissionRepository submissionRepository;


    public BaseResponse login(UserRequest request) throws UserException {
        Optional<Users> user = userRepository.findByUsernameAndPasswordAndRole(request.getUsername(), request.getPassword(), request.getRole());
        if (!user.isEmpty()) {
            return BaseResponse.builder().userId(user.get().getId()).message("Logged in successfully").build();
        }
        throw new UserException("Invalid Credentials.");
    }

    public BaseResponse createUser(UserRequest request) {
        Users user = new Users();
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return BaseResponse.builder().userId(user.getId()).message("User signed up successfully").build();
    }

    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignments addAssignment(AssignmentRequest request, Users createdBy) {
        // Create a new assignment based on the request
        Assignments assignment = new Assignments();
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setDueDate(request.getDueDate());
        assignment.setClassName(request.getClassName());
        assignment.setCreatedBy(createdBy);

        // Save to the database
        return assignmentRepository.save(assignment);
    }
    public Users findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void saveSubmission(Submissions submission) {
        submissionRepository.save(submission);
    }
    public Assignments findAssignmentById(int id) {
        Optional<Assignments> assignment = assignmentRepository.findById(id);
        if (assignment.isPresent()) {
            return assignment.get();
        } else {
            throw new EntityNotFoundException("Assignment not found with ID: " + id);
        }
    }
}
