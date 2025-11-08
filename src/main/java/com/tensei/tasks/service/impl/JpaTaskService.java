package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.FailedAuthenticationException;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.mapper.TaskMapper;
import com.tensei.tasks.repository.TaskRepository;
import com.tensei.tasks.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    /**
     * Save current task
     *
     * @param taskRequest task to save
     * @return saved task
     */
    @Override
    public TaskResponse save(TaskRequest taskRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        Task task = taskMapper.toEntity(taskRequest);

        Task saved = null;

        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            saved = taskRepository.save(task);
        }
        else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            User authenticatedUser = userRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            task.setUser(authenticatedUser);
            saved = taskRepository.save(task);
        }

        if (saved == null)
            throw new FailedAuthenticationException("Authentication error");

        return taskMapper.toDto(saved);
    }

    /**
     * Load the task by marked id
     *
     * @param id task id
     * @return task response
     */
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
