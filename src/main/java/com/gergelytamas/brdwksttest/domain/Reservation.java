package com.gergelytamas.brdwksttest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "reservation")
@SequenceGenerator(
        name = "reservation_id_seq_gen",
        sequenceName = "reservation_id_seq",
        allocationSize = 1)
@NoArgsConstructor
public class Reservation extends BaseEntity implements Serializable {

    @NotNull
    @Column(name = "date_from", nullable = false)
    private Date dateFrom;

    @NotNull
    @Column(name = "date_to", nullable = false)
    private Date dateTo;

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

    public Reservation(
            final Long id,
            final Date dateFrom,
            final Date dateTo,
            final ReservationStatus reservationStatus,
            final ReservationType reservationType,
            final User user,
            final Car car) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.reservationStatus = reservationStatus;
        this.reservationType = reservationType;
        this.user = user;
        this.car = car;
    }
}
