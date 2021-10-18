package com.gergelytamas.brdwksttest.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gergelytamas.brdwksttest.domain.Car;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EquipmentDTO implements Serializable {

    private Long id;

    @NotNull private String description;

    @JsonIgnore private Set<Car> cars = new HashSet<>();
}
