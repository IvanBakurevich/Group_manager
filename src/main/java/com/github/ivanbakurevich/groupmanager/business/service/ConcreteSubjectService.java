package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.rest.dto.ConcreteSubjectDto;

import java.util.List;

public interface ConcreteSubjectService {

    List<ConcreteSubjectDto> getAllConcreteSubjects();

    List<ConcreteSubjectDto> getAvailableConcreteSubjects(String username);

    ConcreteSubjectDto createConcreteSubject(ConcreteSubjectDto concreteSubjectDto, String username);

    boolean deleteConcreteSubject(Integer id, String username);

    boolean addStudentToConcreteSubject(Integer studentId, Integer concreteSubjectId, String username);

    boolean deleteStudentFromConcreteSubject(Integer studentId, Integer concreteSubjectId, String username);

    boolean joinConcreteSubject(Integer concreteSubjectId, String name);

    boolean leaveConcreteSubject(Integer concreteSubjectId, String name);
}
