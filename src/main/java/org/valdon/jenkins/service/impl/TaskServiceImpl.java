package org.valdom.jenkins.service.impl;

import org.valdom.jenkins.entity.TaskEntity;
import org.valdom.jenkins.entity.enums.TaskStatus;
import org.valdom.jenkins.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.valdom.jenkins.repository.TaskRepository;
import org.valdom.jenkins.service.TaskService;

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
