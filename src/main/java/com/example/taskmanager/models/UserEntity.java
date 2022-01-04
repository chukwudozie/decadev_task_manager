package com.example.taskmanager.models;

import lombok.Data;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "user_entity", uniqueConstraints = {@UniqueConstraint(name = "email_constraint",columnNames = "email")})
public class UserEntity {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy  = SEQUENCE, generator = "user_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", nullable = false,columnDefinition = "VARCHAR(45)")
    private String firstname;

    @Column(name = "lastname", nullable = false,columnDefinition = "VARCHAR(45)")
    private String lastname;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(45)")
    private String password;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(45)")
    private String email;

    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(45)")
    private String gender;

    @Column(name = "date_of_birth", nullable = false, columnDefinition = "VARCHAR(45)")
    private String dob;

    @Enumerated(value = EnumType.STRING)
    private Role userRole;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "user_task", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "task_id"))
//    @Column(name = "assigned_tasks")
//    private List<Task> assignedTasks;

    @OneToMany(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "owner",referencedColumnName = "id")
    @Column(name = "owner")
    private List<Task> tasksAssigned;

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
    @Column(name = "created_tasks")
    private List<Task> createdTasks;
}
