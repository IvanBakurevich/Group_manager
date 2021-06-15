package com.github.ivanbakurevich.groupmanager.rest.dto;

import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class SubjectDto {
    private Integer id;
    @NotNull(message = "Title required")
    private String title;
    private boolean isPublic;
    @NotNull(message = "Organization id required")
    private Integer organizationId;
    private List<TopicDto> topics;
}
