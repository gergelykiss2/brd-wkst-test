package service;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import com.gergelytamas.brdwksttest.exception.NotFoundException;
import com.gergelytamas.brdwksttest.repository.ReservationRepository;
import com.gergelytamas.brdwksttest.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Should found all reservations.")
    void foundAllReservationsTest() {

        final List<Reservation> reservations = new ArrayList<>();
        final Reservation firstReservation =
                new Reservation(
                        1L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);
        final Reservation secondReservation =
                new Reservation(
                        2L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.IN_PROGRESS,
                        ReservationType.PERSONAL,
                        null,
                        null);

        reservations.add(firstReservation);
        reservations.add(secondReservation);

        when(reservationRepository.findAll()).thenReturn(reservations);

        final List<Reservation> reservationList = reservationService.findAll();

        verify(reservationRepository, times(1)).findAll();

        assertEquals(2, reservationList.size());
        assertEquals(firstReservation, reservationList.get(0));
        assertEquals(secondReservation, reservationList.get(1));
    }

    @Test
    @DisplayName("Should found reservation by ID.")
    void foundReservationByIdTest() {

        final Reservation reservation =
                new Reservation(
                        1L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        final Reservation foundReservation =
                reservationService.findById(1L).orElseThrow(NotFoundException::new);

        verify(reservationRepository, times(1)).findById(1L);

        assertEquals(reservation, foundReservation);
    }

    @Test
    @DisplayName("Should not found reservation by ID.")
    void notFoundReservationByIdTest() {

        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        final Optional<Reservation> foundReservation = reservationService.findById(1L);

        verify(reservationRepository, times(1)).findById(1L);

        assertEquals(Optional.empty(), foundReservation);
    }

    @Test
    @DisplayName("Should save a reservation entity")
    void createReservationTest() {

        final Reservation reservation =
                new Reservation(
                        1L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        final Reservation savedReservation = reservationService.save(reservation);

        verify(reservationRepository, times(1)).save(reservation);

        assertEquals(reservation, savedReservation);
    }

    @Test
    @DisplayName("Should update a reservation entity")
    void updateReservationTest() {

        final Reservation reservation =
                new Reservation(
                        1L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        reservationService.save(reservation);
        reservation.setReservationStatus(ReservationStatus.IN_PROGRESS);
        reservationService.save(reservation);

        verify(reservationRepository, times(2)).save(reservation);

        assertEquals(reservation.getReservationStatus(), ReservationStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("Should delete a reservation entity.")
    void deleteReservationTest() {

        final Reservation reservation =
                new Reservation(
                        1L,
                        ZonedDateTime.now(),
                        ZonedDateTime.now().plusDays(1),
                        ReservationStatus.BOOKED,
                        ReservationType.WORK,
                        null,
                        null);

        reservationService.save(reservation);
        reservationService.delete(reservation.getId());

        verify(reservationRepository, times(1)).deleteById(reservation.getId());
    }
}
