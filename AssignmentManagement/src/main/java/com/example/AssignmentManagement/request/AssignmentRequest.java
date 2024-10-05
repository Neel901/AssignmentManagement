package com.example.AssignmentManagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {

    private String title;
    private String description;
    private String dueDate;
    private String className;  // 'class' is a reserved keyword, so use 'className' instead
    private int createdBy;
}
