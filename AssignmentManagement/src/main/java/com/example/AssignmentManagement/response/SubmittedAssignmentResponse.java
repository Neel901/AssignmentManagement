package com.example.AssignmentManagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedAssignmentResponse {

    private String assignmentTitle;
    private String studentName;
    private String submissionDate;
}