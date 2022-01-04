package com.example.taskmanager.services;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.UserEntity;

import java.util.List;

public interface UserService {
    boolean createNewUser(UserEntity user);

    UserEntity loginUser(String email, String password);

    UserEntity changeUserToAdmin(UserEntity user);

    List<UserEntity> findAllUsers ();
     UserEntity getUserById(Long id);
    boolean deleteUser(String email);
    UserEntity getUserByEmail(String email);

    UserEntity getUserByRole(Role role);
}
