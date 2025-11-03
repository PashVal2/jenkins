package org.valdom.jenkins.controller;

import org.valdom.jenkins.dto.TaskDto;
import org.valdom.jenkins.dto.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.valdom.jenkins.service.TaskService;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping("/")
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return null;
    }

    @GetMapping("/{id}")
    public TaskDto createTask(@PathVariable Long id) {
        return taskMapper.toDto(taskService.getTaskById(id));
    }

}
