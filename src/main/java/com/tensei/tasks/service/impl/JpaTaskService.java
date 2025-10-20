package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.repository.TaskRepository;
import com.tensei.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaTaskService implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findById(Long id) {
        if (id == null) {
            throw new ResourceNotFoundException("Task ID must be set, got null");
        }
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Task with ID %s not found", id)
        ));
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Task update(Long id, Task task) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

}
