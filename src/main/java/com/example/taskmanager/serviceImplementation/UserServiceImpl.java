package com.example.taskmanager.serviceImplementation;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String ADMIN="ADMIN";
    private static final String DEVELOPER="DEVELOPER";

    UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createNewUser(UserEntity user) {

        UserEntity userFromDb =  userRepository.findUserEntityByEmail(user.getEmail());
        if(userFromDb == null){
            user.setUserRole(Role.DEVELOPER);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity changeUserToAdmin(UserEntity user){
        UserEntity userFromDb = userRepository.findUserEntityByEmail(user.getEmail());
        if(userFromDb != null) {
            user.setUserRole(Role.ADMIN);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<UserEntity> findAllUsers (){
        return userRepository.findAll();
    }

    @Override
    public UserEntity loginUser(String email, String password) {
        UserEntity userFromDb = userRepository.findUserEntityByEmailAndAndPassword(email,password);
        if(userFromDb == null){
            System.err.println("Error in email or password!!! try again");
            return null;
        }
        System.out.println("User Credentials valid for login");
        return  userFromDb;
    }

    @Override
    public UserEntity getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteUser(String email){
        boolean state = false;

        UserEntity userToBeDeleted = userRepository.findUserEntityByEmail(email);
        if(userToBeDeleted != null){
            userRepository.delete(userToBeDeleted);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity getUserByEmail(String email){
        return userRepository.findUserEntityByEmail(email);
    }

    @Override
    public UserEntity getUserByRole(Role role){
        return userRepository.findUserEntityByUserRole(role);
    }
}
