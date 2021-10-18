package com.gergelytamas.brdwksttest.service;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService extends BaseServiceImpl<Reservation, ReservationRepository> {

    public ReservationService(ReservationRepository repository) {
        super(repository);
    }

    public List<Reservation> findAllByReservationStatus(final ReservationStatus reservationStatus) {
        return this.repository.findAllByReservationStatus(reservationStatus);
    }

    public Optional<Reservation> findByUser(final Long user) {
        return this.repository.findByUserId(user);
    }

    public Optional<Reservation> findByCar(final Long car) {
        return this.repository.findByCarId(car);
    }
}
