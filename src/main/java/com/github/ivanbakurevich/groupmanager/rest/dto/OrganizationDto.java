package com.github.ivanbakurevich.groupmanager.rest.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class OrganizationDto {
    private Integer id;
    @NotNull(message = "organization title required")
    private String title;
    private Date createdOn;
}
