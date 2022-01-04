package com.example.taskmanager.serviceImplementation;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setGender("Male");
        user.setPassword("password");
        user.setDob("1990-03-03");
        user.setEmail("user@email.com");
        user.setUserRole(Role.DEVELOPER);
    }

    @Test
    void createNewUser() {

        // Mock the user Repository
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        boolean userCreated = userService.createNewUser(user);

        verify(userRepository,times(1)).save(any(UserEntity.class));

        assertTrue(userCreated);
    }


    @Test
    void loginUser() {

        when(userRepository.findUserEntityByEmailAndAndPassword(anyString(),anyString())).thenReturn(user);

        UserEntity userTest = userRepository.findUserEntityByEmailAndAndPassword("mail@mail.com","123");

        userService.loginUser(user.getEmail(),user.getPassword());

        assertNotNull(userTest);
        assertEquals("user@email.com",user.getEmail());
        assertEquals("password",user.getPassword());
        verify(userRepository,times(1)).findUserEntityByEmailAndAndPassword(user.getEmail(),user.getPassword());

    }


    @Test
    void getUserByEmail() {

        //        mock User Repository
        when(userRepository.findUserEntityByEmail(anyString())).thenReturn(user);

//        Call Method to be Tested
        UserEntity userTest = userRepository.findUserEntityByEmail("mail@mail.com");
        userService.getUserByEmail(user.getEmail());

//        Make Assertion
        assertNotNull(userTest);
        assertEquals("user@email.com",userTest.getEmail());
        verify(userRepository,times(1)).findUserEntityByEmail(user.getEmail());
    }
}