package com.github.ivanbakurevich.groupmanager.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "lesson")
public class LessonEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "duration_mins", nullable = false)
    private Integer duration;

    @Column(name = "datetime", nullable = false)
    private LocalDateTime time;

    @Column(name="location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concrete_subject_id", referencedColumnName = "id", nullable = false)
    private ConcreteSubjectEntity concreteSubject;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<MarkEntity> marks;
}
