package org.valdon.jenkins.controller;

import org.springframework.validation.annotation.Validated;
import org.valdon.jenkins.dto.TaskDto;
import org.valdon.jenkins.dto.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.valdon.jenkins.entity.TaskEntity;
import org.valdon.jenkins.service.TaskService;
import org.valdon.jenkins.validation.OnCreate;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping("/")
    public TaskDto createTask(@Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        TaskEntity task = taskMapper.toEntity(taskDto);
        return taskMapper.toDto(taskService.createTask(task));
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskMapper.toDto(taskService.getTaskById(id));
    }

}
