package com.tensei.tasks.service;

import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.entity.Task;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskService {
    TaskResponse save(TaskRequest taskRequest);
    TaskResponse findById(Long id);
    List<Task> findAll();
    List<Task> findAllByUserId(Long userId);
    Task update(Long id, Task task);
    void deleteById(Integer id);
}
