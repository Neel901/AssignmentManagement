package com.example.AssignmentManagement.controller;


import com.example.AssignmentManagement.exception.UserException;
import com.example.AssignmentManagement.model.Assignments;
import com.example.AssignmentManagement.model.Submissions;
import com.example.AssignmentManagement.model.Users;
import com.example.AssignmentManagement.repository.AssignmentRepository;
import com.example.AssignmentManagement.repository.SubmissionRepository;
import com.example.AssignmentManagement.request.AssignmentRequest;
import com.example.AssignmentManagement.request.SubmissionRequest;
import com.example.AssignmentManagement.request.UserRequest;
import com.example.AssignmentManagement.response.AssignmentResponse;
import com.example.AssignmentManagement.response.BaseResponse;
import com.example.AssignmentManagement.response.SubmittedAssignmentResponse;
import com.example.AssignmentManagement.response.ViewAssignment;
import com.example.AssignmentManagement.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class AppController {

    @Autowired
    public AppService appService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> createUser(@RequestBody UserRequest user) {
        return ResponseEntity.ok(appService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody UserRequest userRequest){
            return ResponseEntity.ok(appService.login(userRequest));
    }

    @PostMapping("/assignments")
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {

            // Fetch the user (teacher) who is creating the assignment
            Users user = appService.findById(assignmentRequest.getCreatedBy());

            // Create the assignment
            Assignments newAssignment = appService.addAssignment(assignmentRequest, user);

            // Create response object
            AssignmentResponse response = new AssignmentResponse(newAssignment.getId(), "Assignment added successfully!");

            return ResponseEntity.ok(response);

    }
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @GetMapping("/submitted-assignments")
    public ResponseEntity<List<Submissions>> getSubmittedAssignments() {
        List<Submissions> submittedAssignments = submissionRepository.findAll();
        return ResponseEntity.ok(submittedAssignments);
    }

    @PostMapping("/submissions")
    public ResponseEntity<String> submitAssignment(@RequestBody SubmissionRequest submissionRequest) {
        // Fetch the user (student) who is submitting the assignment
        Users student = appService.findById(submissionRequest.getStudentId());

        // Fetch the assignment to be submitted
        Assignments assignment = appService.findAssignmentById(submissionRequest.getAssignmentId());

        // Create the submission
        Submissions newSubmission = new Submissions();
        newSubmission.setAssignment(assignment);
        newSubmission.setStudent(student);
        newSubmission.setSubmissionText(submissionRequest.getSubmissionText());

        // Save the submission using the service layer
        appService.saveSubmission(newSubmission);

        return ResponseEntity.ok("Submission successful!");
    }

    @GetMapping("/assignments")
    public ResponseEntity<List<ViewAssignment>> getAllAssignments() {
        List<Assignments> assignments = assignmentRepository.findAll();

        // Convert Assignments to AssignmentResponse
        List<ViewAssignment> response = assignments.stream()
                .map(assignment -> new ViewAssignment(
                        assignment.getId(),
                        assignment.getTitle(),
                        assignment.getDescription(),
                        assignment.getDueDate(),  // Ensure to format it as needed
                        assignment.getClassName(),
                        assignment.getCreatedBy() != null ? assignment.getCreatedBy().getId() : null // Assuming createdBy is a User entity
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


}
