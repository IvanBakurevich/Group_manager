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
@Table(name = "subject")
public class SubjectEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 120, nullable = false)
    private String title;

    @Column(name = "is_public")
    private Boolean isPublic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subject_topic",
            joinColumns = {@JoinColumn(name = "subject_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "topic_id", referencedColumnName = "id")}
    )
    private List<TopicEntity> topics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "Id")
    private OrganizationEntity organization;
}
