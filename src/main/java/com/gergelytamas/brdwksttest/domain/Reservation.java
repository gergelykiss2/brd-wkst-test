package com.gergelytamas.brdwksttest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "reservation", schema = "public")
@SequenceGenerator(
        name = "reservation_id_seq_gen",
        sequenceName = "reservation_id_seq",
        allocationSize = 1)
public class Reservation extends BaseEntity implements Serializable {

    @NotNull
    @Column(name = "date_from", nullable = false)
    private ZonedDateTime dateFrom;

    @NotNull
    @Column(name = "date_to", nullable = false)
    private ZonedDateTime dateTo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status", nullable = false)
    private ReservationStatus reservationStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_type", nullable = false)
    private ReservationType reservationType;

    @ManyToOne
    @JsonIgnoreProperties(value = "reservations", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "reservations", allowSetters = true)
    private Car car;

    @Builder
    public Reservation(
            final Integer id,
            final ZonedDateTime dateFrom,
            final ZonedDateTime dateTo,
            final ReservationStatus reservationStatus,
            final ReservationType reservationType,
            final User user,
            final Car car,
            final ZonedDateTime createdOn,
            final ZonedDateTime modifiedOn) {
        super(id, createdOn, modifiedOn);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reservationStatus = reservationStatus;
        this.reservationType = reservationType;
        this.user = user;
        this.car = car;
    }
}
