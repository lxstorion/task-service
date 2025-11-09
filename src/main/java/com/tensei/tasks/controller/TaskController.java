package com.tensei.tasks.controller;

import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.dto.tasks.TaskUpdateRequest;
import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor()
public class TaskController {

    private final TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> createTask(@Validated @RequestBody TaskRequest taskRequest) {

        TaskResponse created = taskService.save(taskRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long taskId) {

        TaskResponse response = taskService.findById(taskId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskResponse>> getAllTasks(Pageable pageable) {

        List<TaskResponse> fetched = taskService.findAll(pageable);
        return new ResponseEntity<>(fetched, HttpStatus.OK);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskResponse> updateTask(@RequestParam("id") Long id,
                                        @RequestBody TaskUpdateRequest task) {

        TaskResponse response = taskService.update(id, task);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam("id") Long id) {

        taskService.deleteById(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
