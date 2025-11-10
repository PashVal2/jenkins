package org.valdon.jenkins.dto;

import org.valdon.jenkins.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.valdon.jenkins.validation.OnCreate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    @Null(message = "id must be null", groups = {OnCreate.class})
    private Long id;

    @NotNull(message = "title must be not null", groups = {OnCreate.class})
    private String title;

    private String description;

    private TaskStatus taskStatus;

}
