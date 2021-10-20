package com.gergelytamas.brdwksttest.repository;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(
            "select reservation from Reservation reservation where reservation.reservationStatus =: reservation_status")
    List<Reservation> findAllByReservationStatus(
            @Param("reservation_status") final ReservationStatus reservationStatus);

    @Query("select reservation from Reservation reservation where reservation.user.id =: userId")
    Optional<Reservation> findByUserId(@Param("userId") final Integer personId);

    @Query("select reservation from Reservation reservation where reservation.car.id =: carId")
    Optional<Reservation> findByCarId(@Param("carId") final Integer carId);
}
