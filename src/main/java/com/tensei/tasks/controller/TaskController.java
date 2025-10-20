package com.tensei.tasks.controller;

import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor()
public class TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody Task task) {

        Task created = taskService.save(task);
        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTaskById(@RequestParam("id") Long id) {

        Task task = taskService.findById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTask(@RequestParam("id") Long id, @RequestBody Task task) {

        return null;

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam("id") Long id) {

        return null;

    }

}
