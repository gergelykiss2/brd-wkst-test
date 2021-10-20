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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    private ReservationService reservationService;

    @Mock private ReservationRepository reservationRepository;

    private Reservation firstReservation;
    private Reservation secondReservation;

    @BeforeEach
    void initTest() {
        this.reservationService = new ReservationService(reservationRepository);

        this.firstReservation =
                Reservation.builder()
                        .id(1)
                        .dateFrom(ZonedDateTime.now())
                        .dateTo(ZonedDateTime.now())
                        .reservationStatus(ReservationStatus.BOOKED)
                        .reservationType(ReservationType.WORK)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();

        this.secondReservation =
                Reservation.builder()
                        .id(2)
                        .dateFrom(ZonedDateTime.now().plusDays(2))
                        .dateTo(ZonedDateTime.now().plusDays(4))
                        .reservationStatus(ReservationStatus.IN_PROGRESS)
                        .reservationType(ReservationType.PERSONAL)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();
    }

    @Test
    @DisplayName("Should found all reservations.")
    void foundAllReservationsTest() {

        final List<Reservation> reservations = new ArrayList<>();
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

        when(reservationRepository.findById(1)).thenReturn(Optional.of(firstReservation));

        final Reservation foundReservation =
                reservationService.findById(1).orElseThrow(NotFoundException::new);

        verify(reservationRepository, times(1)).findById(1);

        assertEquals(firstReservation, foundReservation);
    }

    @Test
    @DisplayName("Should not found reservation by ID.")
    void notFoundReservationByIdTest() {

        when(reservationRepository.findById(1)).thenReturn(Optional.empty());

        final Optional<Reservation> foundReservation = reservationService.findById(1);

        verify(reservationRepository, times(1)).findById(1);

        assertEquals(Optional.empty(), foundReservation);
    }

    @Test
    @DisplayName("Should save a reservation entity")
    void createReservationTest() {

        when(reservationRepository.save(firstReservation)).thenReturn(firstReservation);

        final Reservation savedReservation = reservationService.save(firstReservation);

        verify(reservationRepository, times(1)).save(firstReservation);

        assertEquals(firstReservation, savedReservation);
    }

    @Test
    @DisplayName("Should update a reservation entity")
    void updateReservationTest() {

        when(reservationRepository.save(firstReservation)).thenReturn(firstReservation);

        reservationService.save(firstReservation);
        firstReservation.setReservationStatus(ReservationStatus.IN_PROGRESS);
        reservationService.save(firstReservation);

        verify(reservationRepository, times(2)).save(firstReservation);

        assertEquals(ReservationStatus.IN_PROGRESS, firstReservation.getReservationStatus());
    }

    @Test
    @DisplayName("Should delete a reservation entity.")
    void deleteReservationTest() {

        reservationService.save(firstReservation);
        reservationService.delete(firstReservation.getId());

        verify(reservationRepository, times(1)).deleteById(firstReservation.getId());
    }
}
