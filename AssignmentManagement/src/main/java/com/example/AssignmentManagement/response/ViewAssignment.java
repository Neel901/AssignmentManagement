package com.example.AssignmentManagement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewAssignment {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String className;
    private int createdBy;
}
