package com.github.ivanbakurevich.groupmanager.dao.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", length = 120, unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", length = 120)
    private String firstName;

    @Column(name = "last_name", length = 120)
    private String lastName;

    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "Role_Id", referencedColumnName = "Id", nullable = false, updatable = false)
    private RoleEntity role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_user",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")}
    )
    private List<GroupEntity> groups;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<OrganizationEntity> organizations;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<MarkEntity> marks;
}