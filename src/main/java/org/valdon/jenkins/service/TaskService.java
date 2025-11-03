package org.valdon.jenkins.service;

import org.valdon.jenkins.entity.TaskEntity;

public interface TaskService {

    TaskEntity createTask(TaskEntity task);

    TaskEntity getTaskById(Long taskId);

}
