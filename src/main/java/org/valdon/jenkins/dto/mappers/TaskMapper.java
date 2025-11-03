package org.valdom.jenkins.dto.mappers;

import org.valdom.jenkins.dto.TaskDto;
import org.valdom.jenkins.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<TaskEntity, TaskDto> {

}
