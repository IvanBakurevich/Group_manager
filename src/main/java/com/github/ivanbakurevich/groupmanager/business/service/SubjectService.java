package com.github.ivanbakurevich.groupmanager.business.service;

import com.github.ivanbakurevich.groupmanager.rest.dto.SubjectDto;

import java.util.List;

public interface SubjectService {

    List<SubjectDto> getSubjectsByUsername(String username);

    List<SubjectDto> getPrivateSubjectsByUsername(String username);

    SubjectDto getSubjectById(Integer subjectId, String username);

    List<SubjectDto> getSubjectsByOrganizationId(Integer organizationId, String username);

    SubjectDto addSubject(SubjectDto subject, String username);

    boolean deleteSubject(Integer subjectId, String username);

    boolean addTopicToSubject(Integer topicId, Integer subjectId, String username);

    boolean deleteTopicFromSubject(Integer topicId, Integer subjectId, String username);
}
