package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ReservationDTO extends BaseDTO implements Serializable {

    @NotNull private ZonedDateTime dateFrom;

    @NotNull private ZonedDateTime dateTo;

    @NotNull private ReservationStatus reservationStatus;

    @NotNull private ReservationType reservationType;

    private Integer userId;

    private Integer carId;
}
