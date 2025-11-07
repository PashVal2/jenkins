package org.valdon.jenkins.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.valdon.jenkins.entity.TaskEntity;
import org.valdon.jenkins.entity.enums.TaskStatus;
import org.valdon.jenkins.exception.ResourceNotFoundException;
import org.valdon.jenkins.repository.TaskRepository;
import org.valdon.jenkins.service.impl.TaskServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskEntity testTask;

    @BeforeEach
    void setUp() {
        testTask = new TaskEntity();
        testTask.setId(1L);
        testTask.setTitle("test");
        testTask.setTaskStatus(TaskStatus.NEW);
    }

    @Test
    void createTaskShouldCreateTask() {
        when(taskRepository.save(any(TaskEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        TaskEntity task = taskService.createTask(testTask);

        assertEquals(task, testTask);
    }

    @Test
    void getTaskShouldGetTaskTaskExists() {
        when(taskRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(testTask));

        TaskEntity task = taskService.getTaskById(testTask.getId());

        assertEquals(task.getId(), testTask.getId());
    }

    @Test
    void getTaskShouldNotGetTaskTaskNotExists() {
        when(taskRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getTaskById(testTask.getId())
        );
    }

}
