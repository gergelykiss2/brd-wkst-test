package service;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import com.gergelytamas.brdwksttest.repository.ReservationRepository;
import com.gergelytamas.brdwksttest.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    ReservationService reservationService;

    @Mock ReservationRepository reservationRepository;

    @BeforeEach
    void initTest() {
        this.reservationService = new ReservationService(reservationRepository);
    }

    @Test
    @DisplayName("Should get all reservations.")
    void getAllReservationsTest() {
        final List<Reservation> reservations = new ArrayList<>();
        final Reservation firstReservation =
                new Reservation(
                        1L,
                        new Date(System.currentTimeMillis()),
                        new Date(System.currentTimeMillis() + 86400000),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);
        final Reservation secondReservation =
                new Reservation(
                        2L,
                        new Date(System.currentTimeMillis()),
                        new Date(System.currentTimeMillis() + 86400000),
                        ReservationStatus.IN_PROGRESS,
                        ReservationType.PERSONAL,
                        null,
                        null);

        reservations.add(firstReservation);
        reservations.add(secondReservation);

        when(reservationRepository.findAll()).thenReturn(reservations);

        final List<Reservation> reservationList = reservationService.findAll();

        verify(reservationRepository, times(1)).findAll();

        Assertions.assertEquals(2, reservationList.size());
        Assertions.assertEquals(firstReservation, reservationList.get(0));
        Assertions.assertEquals(secondReservation, reservationList.get(1));
    }

    @Test
    @DisplayName("Should get reservation by ID.")
    void getCarByIdTest() {
        when(reservationRepository.findById(1L))
                .thenReturn(
                        Optional.of(
                                new Reservation(
                                        1L,
                                        new Date(System.currentTimeMillis()),
                                        new Date(System.currentTimeMillis() + 86400000),
                                        ReservationStatus.BOOKED,
                                        ReservationType.WORK,
                                        null,
                                        null)));

        final Reservation reservation = reservationService.findById(1L).get();

        verify(reservationRepository, times(1)).findById(1L);

        //        Assertions.assertEquals("ABC123", reservation.getLicensePlate());
        //        Assertions.assertEquals("Ford", reservation.getMake());
        //        Assertions.assertEquals("S-Max", reservation.getModel());
        //        Assertions.assertEquals(FuelType.DIESEL, reservation.getFuelType());
        //        Assertions.assertEquals(CarStatus.AVAILABLE, reservation.getCarStatus());
    }
}
