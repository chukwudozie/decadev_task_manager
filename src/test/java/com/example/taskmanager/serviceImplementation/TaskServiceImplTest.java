package com.example.taskmanager.serviceImplementation;

import com.example.taskmanager.models.Role;
import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.UserEntity;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskServiceImpl taskService;

    private Task task;
    private UserEntity user;
    @BeforeEach
    void setUp() {

        user = new UserEntity();
        user.setUserRole(Role.ADMIN);
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setEmail("user@email.com");
        task = new Task();
        task.setActive(false);
        task.setName("new task");
        task.setDescription("executing");
        task.setAdmin(user);

    }

    @Test
    void createNewTask() {

        when(repository.save(any(Task.class))).thenReturn(task);

        Task taskCreated = taskService.createNewTask(task,user);

        assertNotNull(taskCreated);
        assertEquals("new task",taskCreated.getName());
        assertEquals("executing",taskCreated.getDescription());

        verify(repository,times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById() {
    }
}