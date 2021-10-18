package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CarDTO implements Serializable {

    private Long id;

    @NotNull private String licensePlate;

    @NotNull private String make;

    @NotNull private String model;

    @NotNull private FuelType fuelType;

    @NotNull private CarStatus carStatus;

    private Set<Reservation> reservations = new HashSet<>();
}
