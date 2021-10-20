package com.gergelytamas.brdwksttest.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gergelytamas.brdwksttest.domain.Car;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EquipmentDTO implements Serializable {

    private Integer id;

    @NotBlank private String description;

    @JsonIgnore private Set<Car> cars = new HashSet<>();
}
