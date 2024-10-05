package com.example.AssignmentManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignments assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Users student;

    @Column(columnDefinition = "TEXT")
    private String submissionText;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;

    @PrePersist
    protected void onCreate() {
        submissionDate = new Date();
    }
}
