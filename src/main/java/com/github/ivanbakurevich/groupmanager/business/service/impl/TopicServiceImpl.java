package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.TopicService;
import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.dao.entity.OrganizationEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.TopicEntity;
import com.github.ivanbakurevich.groupmanager.dao.repository.OrganizationRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.SubjectRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.TopicRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.TopicDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final OrganizationRepository organizationRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public TopicServiceImpl(TopicRepository topicRepository, OrganizationRepository organizationRepository, SubjectRepository subjectRepository, UserService userService, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.organizationRepository = organizationRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll()
                .stream()
                .map(topic -> modelMapper.map(topic, TopicDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TopicDto> getAllTopicsByOrganizationId(Integer organizationId, String username) {
        Optional<OrganizationEntity> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isPresent() && userService.isUserInOrganization(username, organizationOptional.get().getId())) {
            return organizationOptional.map(organizationEntity -> topicRepository.findAllByOrganization(organizationEntity)
                    .stream()
                    .map(topic -> modelMapper.map(topic, TopicDto.class))
                    .collect(Collectors.toList())).orElse(null);
        }
        return null;
    }

//    @Override
//    public List<TopicDto> getAllTopicsBySubjectId(Integer subjectId) {
//        Optional<SubjectEntity> optional = subjectRepository.findById(subjectId);
//
//        return optional.map(subj -> topicRepository.findAllBySubjectId(subj.getId())
//                .stream()
//                .map(topic -> modelMapper.map(topic, TopicDto.class))
//                .collect(Collectors.toList())).orElse(null);
//    }

    @Override
    public TopicDto addTopic(TopicDto topicDto, String username) {
        if (userService.isUserInOrganization(username, topicDto.getOrganizationId())) {
            TopicEntity topicEntity = modelMapper.map(topicDto, TopicEntity.class);
            topicRepository.save(topicEntity);
            return topicDto;
        }
        return null;
    }

    @Override
    public boolean deleteTopic(Integer id, String username) {
        Optional<TopicEntity> byId = topicRepository.findById(id);
        if (byId.isPresent() && userService.isUserInOrganization(username, byId.get().getOrganization().getId())) {
            topicRepository.delete(byId.get());
            return true;
        }
        return false;
    }

    @Override
    public TopicDto updateTopic(TopicDto topicDto, String username) {
        Optional<TopicEntity> byId = topicRepository.findById(topicDto.getId());
        if (byId.isPresent() && userService.isUserInOrganization(username, byId.get().getOrganization().getId())) {
            TopicEntity saved = topicRepository.save(byId.get());
            return modelMapper.map(saved, TopicDto.class);
        }
        return null;
    }
}
