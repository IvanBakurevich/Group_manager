package com.github.ivanbakurevich.groupmanager.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "concrete_subject")
public class ConcreteSubjectEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "Id")
    private UserEntity teacher;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "Id")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "Id")
    private SubjectEntity subject;

    @OneToMany(mappedBy = "concreteSubject", fetch = FetchType.LAZY)
    private List<LessonEntity> lessons;
}
