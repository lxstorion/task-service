package com.tensei.tasks.service;

import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.dto.tasks.TaskUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    TaskResponse save(TaskRequest taskRequest);
    TaskResponse findById(Long id);
    List<TaskResponse> findAll(Pageable pageable);
    TaskResponse update(Long id, TaskUpdateRequest task);
    void deleteById(Integer id);
}
