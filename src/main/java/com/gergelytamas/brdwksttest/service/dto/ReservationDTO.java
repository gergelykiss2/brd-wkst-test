package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ReservationDTO implements Serializable {

    private Long id;

    @NotNull private ZonedDateTime dateFrom;

    @NotNull private ZonedDateTime dateTo;

    @NotNull private ReservationStatus reservationStatus;

    @NotNull private ReservationType reservationType;

    private Long userId;

    private Long carId;
}
