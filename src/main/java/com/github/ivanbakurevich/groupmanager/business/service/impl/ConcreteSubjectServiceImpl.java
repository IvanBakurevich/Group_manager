package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.ConcreteSubjectService;
import com.github.ivanbakurevich.groupmanager.dao.repository.ConcreteSubjectRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.UserRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.ConcreteSubjectDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcreteSubjectServiceImpl implements ConcreteSubjectService {

    private final ConcreteSubjectRepository concreteSubjectRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ConcreteSubjectServiceImpl(ConcreteSubjectRepository concreteSubjectRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.concreteSubjectRepository = concreteSubjectRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ConcreteSubjectDto> getAllConcreteSubjects() {
        return concreteSubjectRepository.findAll().stream()
                .map(s -> modelMapper.map(s, ConcreteSubjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ConcreteSubjectDto> getAvailableConcreteSubjects(String username) {
        List<Integer> organizationIds = userRepository.getOrganizationIds(username);

        return concreteSubjectRepository.findAll().stream()
                .map(s -> modelMapper.map(s, ConcreteSubjectDto.class))
                .filter(cs -> organizationIds.contains(cs.getSubject().getOrganizationId()) || cs.getSubject().isPublic())
                .collect(Collectors.toList());
    }

    @Override
    public ConcreteSubjectDto createConcreteSubject(ConcreteSubjectDto concreteSubjectDto, String username) {
        return null;
    }

    @Override
    public boolean deleteConcreteSubject(Integer id, String name) {
        return false;
    }

    @Override
    public boolean addStudentToConcreteSubject(Integer studentId, Integer concreteSubjectId, String name) {
        return false;
    }

    @Override
    public boolean deleteStudentFromConcreteSubject(Integer studentId, Integer concreteSubjectId, String name) {
        return false;
    }

    @Override
    public boolean joinConcreteSubject(Integer concreteSubjectId, String name) {
        return false;
    }

    @Override
    public boolean leaveConcreteSubject(Integer concreteSubjectId, String name) {
        return false;
    }
}
