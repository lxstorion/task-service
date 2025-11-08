package com.tensei.tasks.mapper;

import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toDto(Task task);
    Task toEntity(TaskRequest taskRequest);
    
}
