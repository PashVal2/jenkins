package org.valdon.jenkins.service.impl;

import org.valdon.jenkins.entity.TaskEntity;
import org.valdon.jenkins.entity.enums.TaskStatus;
import org.valdon.jenkins.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.valdon.jenkins.repository.TaskRepository;
import org.valdon.jenkins.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskEntity createTask(TaskEntity task) {
        if (task.getId() != null) {
            task.setId(null);
        }
        if (task.getTaskStatus() == null) {
            task.setTaskStatus(TaskStatus.NEW);
        }
        return taskRepository.save(task);
    }

    @Override
    public TaskEntity getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));
    }

}
