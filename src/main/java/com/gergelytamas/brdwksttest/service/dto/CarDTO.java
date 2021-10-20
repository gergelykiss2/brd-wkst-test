package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CarDTO extends BaseDTO implements Serializable {

    @NotBlank private String licensePlate;

    @NotBlank private String make;

    @NotBlank private String model;

    @NotNull private FuelType fuelType;

    @NotNull private CarStatus carStatus;

    private Set<Reservation> reservations = new HashSet<>();
}
