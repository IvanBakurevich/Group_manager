package com.github.ivanbakurevich.groupmanager.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "organization")
public class OrganizationEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 120, unique = true, nullable = false)
    private String title;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "organization_user",
            joinColumns = {@JoinColumn(name = "organization_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<UserEntity> users;
}
