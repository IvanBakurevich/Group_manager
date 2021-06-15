package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class ConcreteSubjectDto {
    private Integer id;
    private UserDto teacher;
    //private List<UserDto> students;
    private SubjectDto subject;
    private List<LessonDto> lessons;
}
