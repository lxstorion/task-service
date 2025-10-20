package com.tensei.tasks.controller;

import com.tensei.tasks.domain.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor()
public class TaskController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTask(@RequestBody Task task) {

        return null;

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTaskById(@RequestParam("id") Integer id) {

        return null;

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTask(@RequestParam("id") Integer id, @RequestBody Task task) {

        return null;

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam("id") Integer id) {

        return null;

    }

}
