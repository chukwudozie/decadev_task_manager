package com.example.taskmanager.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private boolean active;

    @Column(name = "creation")
    @CreationTimestamp
    private LocalDate creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deadline")
    private LocalDate deadline;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "project_origin")
//    private Project projectOrigin;

//    @ManyToMany(mappedBy = "assignedTasks")
//    @Column(name = "assigned_to")
//    private List<UserEntity> userCollaborators;

    @ManyToOne
    @JoinColumn(name = "owner",referencedColumnName = "id")
    private UserEntity userAssigned;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin",referencedColumnName = "id")
    private UserEntity admin;

    public long daysLeftUntilDeadline(LocalDate date, Task task) {
        return ChronoUnit.DAYS.between(task.getCreationDate(), date);
    }

}
