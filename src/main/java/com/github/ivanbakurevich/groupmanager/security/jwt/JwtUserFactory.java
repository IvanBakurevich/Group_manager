package com.github.ivanbakurevich.groupmanager.security.jwt;

import com.github.ivanbakurevich.groupmanager.dao.entity.RoleEntity;
import com.github.ivanbakurevich.groupmanager.dao.entity.UserEntity;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(UserEntity user) {
        return new JwtUser(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                true,
                new Date(),
                mapToGrantedAuthorities(user.getRole()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(RoleEntity userRole) {
        return Collections.singletonList(
                new SimpleGrantedAuthority(userRole.getRoleName().name()));
    }
}
