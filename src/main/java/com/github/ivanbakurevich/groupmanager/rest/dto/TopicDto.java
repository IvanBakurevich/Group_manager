package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
public class TopicDto {
    private Integer id;
    @NotNull(message = "Title required")
    private String title;
    private String description;
    @NotNull(message = "Organization id required")
    private Integer organizationId;
}
