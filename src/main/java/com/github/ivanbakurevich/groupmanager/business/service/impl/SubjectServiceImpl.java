package com.github.ivanbakurevich.groupmanager.business.service.impl;

import com.github.ivanbakurevich.groupmanager.business.service.SubjectService;
import com.github.ivanbakurevich.groupmanager.business.service.UserService;
import com.github.ivanbakurevich.groupmanager.dao.entity.OrganizationEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.SubjectEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.TopicEntity;
import com.github.ivanbakurevich.groupmanager.dao.repository.OrganizationRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.SubjectRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.TopicRepository;
import com.github.ivanbakurevich.groupmanager.dao.repository.UserRepository;
import com.github.ivanbakurevich.groupmanager.rest.dto.SubjectDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public SubjectServiceImpl(SubjectRepository subjectRepository, TopicRepository topicRepository, OrganizationRepository organizationRepository, UserRepository userRepository, ModelMapper modelMapper, UserService userService) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public SubjectDto addSubject(SubjectDto subject, String username) {
        if (userService.isUserInOrganization(username, subject.getOrganizationId())) {
            SubjectEntity subjectEntity = modelMapper.map(subject, SubjectEntity.class);
            subjectRepository.save(subjectEntity);
            return subject;
        }
        return null;
    }

    @Override
    public boolean deleteSubject(Integer subjectId, String username) {
        Optional<SubjectEntity> subjOpt = subjectRepository.findById(subjectId);

        if (subjOpt.isPresent() && userService.isUserInOrganization(username, subjOpt.get().getOrganization().getId())) {
            subjectRepository.delete(subjOpt.get());
            return true;
        }

        return false;
    }

    @Override
    public boolean addTopicToSubject(Integer topicId, Integer subjectId, String username) {
        Optional<TopicEntity> topicOpt = topicRepository.findById(topicId);
        Optional<SubjectEntity> subjOpt = subjectRepository.findById(subjectId);

        if (subjOpt.isPresent() && topicOpt.isPresent()
                && userService.isUserInOrganization(username, subjOpt.get().getOrganization().getId())
                && subjOpt.get().getOrganization().equals(topicOpt.get().getOrganization())) {
            SubjectEntity subject = subjOpt.get();
            List<TopicEntity> topics = subject.getTopics();
            topics.add(topicOpt.get());
            subject.setTopics(topics);

            subjectRepository.save(subject);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTopicFromSubject(Integer topicId, Integer subjectId, String username) {
        Optional<TopicEntity> topicOpt = topicRepository.findById(topicId);
        Optional<SubjectEntity> subjOpt = subjectRepository.findById(subjectId);

        if (subjOpt.isPresent() && topicOpt.isPresent()
                && userService.isUserInOrganization(username, subjOpt.get().getOrganization().getId())
                && subjOpt.get().getOrganization().equals(topicOpt.get().getOrganization())) {
            SubjectEntity subject = subjOpt.get();
            List<TopicEntity> topics = subject.getTopics();
            if (topics.remove(topicOpt.get())) {
                subject.setTopics(topics);
                subjectRepository.save(subject);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SubjectDto> getSubjectsByUsername(String username) {
        List<Integer> organizationIds = userRepository.getOrganizationIds(username);

        List<SubjectEntity> subjects = getPublicSubjects();
        subjects.addAll(getAllSubjects(organizationIds));

        return subjects.stream()
                .distinct()
                .map(subj -> modelMapper.map(subj, SubjectDto.class))
                .sorted(Comparator.comparingInt(SubjectDto::getOrganizationId))
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDto> getPrivateSubjectsByUsername(String username) {
        List<Integer> organizationIds = userRepository.getOrganizationIds(username);
        List<SubjectEntity> subjects = getAllSubjects(organizationIds);

        return subjects.stream()
                .distinct()
                .map(subj -> modelMapper.map(subj, SubjectDto.class))
                .collect(Collectors.toList());
    }


    private List<SubjectEntity> getPublicSubjects() {
        return subjectRepository.findAllPublic();
    }

    private List<SubjectEntity> getAllSubjects(List<Integer> organizationIds) {
        return subjectRepository.findAllByOrganizationIds(organizationIds);
    }

    @Override
    public SubjectDto getSubjectById(Integer subjectId, String username) {
        Optional<SubjectEntity> subjOpt = subjectRepository.findById(subjectId);

        if (subjOpt.isPresent() && userService.isUserInOrganization(username, subjOpt.get().getOrganization().getId())) {
            return modelMapper.map(subjOpt.get(), SubjectDto.class);
        }

        return null;
    }

    @Override
    public List<SubjectDto> getSubjectsByOrganizationId(Integer organizationId, String username) {
        Optional<OrganizationEntity> orgOpt = organizationRepository.findById(organizationId);

        if (orgOpt.isPresent()) {
            List<SubjectDto> subjects = subjectRepository.findAllByOrganization(orgOpt.get()).stream()
                    .map(subj -> modelMapper.map(subj, SubjectDto.class))
                    .collect(Collectors.toList());

            if (!userService.isUserInOrganization(username, orgOpt.get().getId())) {
                subjects = subjects.stream().filter(SubjectDto::isPublic).collect(Collectors.toList());
            }
            return subjects;
        }

        return null;
    }
//    public Boolean isSubjectInOrganization(Integer subjectId, Integer OrganizationId) {
//        Optional<SubjectEntity> subjById = subjectRepository.findById(subjectId);
//        Optional<OrganizationEntity> byId = organizationRepository.findById(OrganizationId);
//        if (subjById.isPresent() && byId.isPresent()) {
//            List<SubjectEntity> allByOrganization = subjectRepository.findAllByOrganization(byId.get());
//            if (allByOrganization.contains(subjById.get())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    public SubjectDto getByTitleAndOrganizationId(String title, Integer organizationId) {
//        Optional<OrganizationEntity> orgOpt = organizationRepository.findById(organizationId);
//
//        if (orgOpt.isPresent()) {
//            Optional<SubjectEntity> subjectOpt = subjectRepository.findByTitleAndOrganization(title, orgOpt.get());
//            if (subjectOpt.isPresent()) {
//                return modelMapper.map(subjectOpt.get(), SubjectDto.class);
//            }
//        }
//        return null;
//    }
}
