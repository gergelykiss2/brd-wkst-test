package com.gergelytamas.brdwksttest.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class BaseDTO {

    private Integer id;

    private ZonedDateTime createdOn;

    private ZonedDateTime modifiedOn;
}
