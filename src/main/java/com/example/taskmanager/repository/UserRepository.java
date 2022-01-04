package com.example.taskmanager.repository;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByEmail(String email);// For User registration
    UserEntity findUserEntityByEmailAndAndPassword(String email, String password); //For Login
    UserEntity findUserEntityByUserRole(Role role);

}
