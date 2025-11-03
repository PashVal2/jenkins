package org.valdon.jenkins.dto.mappers;

import org.valdon.jenkins.dto.TaskDto;
import org.valdon.jenkins.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<TaskEntity, TaskDto> {

}
