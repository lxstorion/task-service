package com.tensei.tasks.service;

import com.tensei.tasks.domain.entity.Task;

import java.util.List;

public interface TaskService {
    Task save(Task task);
    Task findById(Long id);
    List<Task> findAll();
    List<Task> findAllByUserId(Long userId);
    Task update(Long id, Task task);
    void deleteById(Integer id);
}
