package com.tensei.tasks.repository;

import com.tensei.tasks.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository
        extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    Optional<Task> findByIdAndUserUsername(Long id, String username);

    Page<Task> findByUserUsername(String username, Pageable pageable);

}
