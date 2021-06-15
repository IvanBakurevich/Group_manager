package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class LessonDto {
    private Integer id;
    private Integer duration;
    private LocalDateTime time;
    private String location;
}
