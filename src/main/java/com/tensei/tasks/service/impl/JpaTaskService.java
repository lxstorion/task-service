package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.dto.auth.UserPrincipal;
import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.dto.tasks.TaskUpdateRequest;
import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.domain.entity.User;
import com.tensei.tasks.exception.FailedAuthenticationException;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.mapper.TaskMapper;
import com.tensei.tasks.repository.TaskRepository;
import com.tensei.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        User authenticatedUser = (User) auth.getPrincipal();
        task.setUser(authenticatedUser);
        taskRepository.save(task);


        return taskMapper.toDto(task);
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

        Task task = isAdmin(authorities)
                ? taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id))
                : taskRepository.findByIdAndUserUsername(id, auth.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return taskMapper.toDto(task);
    }

    /**
     * Finds all users. Only accessible to user with ROLE_ADMIN authorities
     *
     * @param pageable paging
     * @return list of TaskResponse elements
     */
    @Override
    public List<TaskResponse> findAll(Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id"));

        return isAdmin(authorities)

                ? taskRepository.findAll(pageRequest).getContent().stream()
                                .map(taskMapper::toDto)
                                .toList()

                : taskRepository.findByUserUsername(auth.getName(), pageRequest).getContent().stream()
                                .map(taskMapper::toDto)
                                .toList();

    }

    /**
     * Update task data with specified id
     *
     * @param id identifier of task
     * @param request new task data
     * @return task update response
     */
    @Override
    public TaskResponse update(Long id, TaskUpdateRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        Task existed = isAdmin(authorities)
                ? taskRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id))

                : taskRepository.findByIdAndUserUsername(id, auth.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        setFields(existed, request);

        return taskMapper.toDto(taskRepository.save(existed));

    }

    /**
     * Delete task with specified id
     *
     * @param id id of task to delete
     */
    @Override
    public void deleteById(Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        if (isAdmin(authorities)) {
            taskRepository.deleteById(id);
            return;
        }

        Task task = taskRepository.findByIdAndUserUsername(id, auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        taskRepository.delete(task);

    }

    private boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private void setFields(Task task, TaskUpdateRequest taskUpdateRequest) {
        task.setTitle(taskUpdateRequest.title());
        task.setDescription(taskUpdateRequest.description());
        task.setDone(taskUpdateRequest.done());
    }

}
