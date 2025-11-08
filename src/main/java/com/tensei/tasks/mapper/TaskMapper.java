package com.tensei.tasks.mapper;

import com.tensei.tasks.domain.dto.tasks.TaskOwnerResponse;
import com.tensei.tasks.domain.dto.tasks.TaskRequest;
import com.tensei.tasks.domain.dto.tasks.TaskResponse;
import com.tensei.tasks.domain.entity.Task;
import com.tensei.tasks.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user", target = "owner")
    TaskResponse toDto(Task task);

    Task toEntity(TaskRequest taskRequest);
    TaskOwnerResponse ownerToTaskOwner(User user);

}
