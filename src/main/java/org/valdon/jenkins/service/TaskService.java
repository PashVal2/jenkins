package org.valdom.jenkins.service;

import org.valdom.jenkins.entity.TaskEntity;

public interface TaskService {

    TaskEntity createTask(TaskEntity task);

    TaskEntity getTaskById(Long taskId);

}
