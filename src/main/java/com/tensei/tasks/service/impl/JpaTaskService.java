package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.mapper.TaskMapper;
import com.tensei.tasks.repository.TaskRepository;
import com.tensei.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JpaTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public TaskResponse findById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        Task task = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ? taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id))
                : taskRepository.findByIdAndUserUsername(id, auth.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return taskMapper.toDto(task);
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
