package controller;

import com.gergelytamas.brdwksttest.BrdWkstTestApplication;
import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.ReservationType;
import com.gergelytamas.brdwksttest.repository.ReservationRepository;
import com.gergelytamas.brdwksttest.service.dto.ReservationDTO;
import com.gergelytamas.brdwksttest.service.mapper.ReservationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import static controller.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BrdWkstTestApplication.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class ReservationControllerITTest {

    @Autowired private EntityManager em;

    @Autowired private MockMvc restReservationControllerMockMvc;

    @Autowired private ReservationRepository reservationRepository;

    @Autowired private ReservationMapper reservationMapper;

    private Reservation reservation;

    @BeforeEach
    void initTest() {
        this.reservation =
                Reservation.builder()
                        .dateFrom(ZonedDateTime.now())
                        .dateTo(ZonedDateTime.now())
                        .reservationStatus(ReservationStatus.BOOKED)
                        .reservationType(ReservationType.WORK)
                        .createdOn(ZonedDateTime.now())
                        .modifiedOn(ZonedDateTime.now())
                        .build();
    }

    @Test
    @Transactional
    void createReservation() throws Exception {
        // Initialize database
        final int reservationDatabaseSizeBeforeCreate = reservationRepository.findAll().size();

        final ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // Create the reservation entity
        restReservationControllerMockMvc
                .perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(reservationDTO)))
                .andExpect(status().isCreated());

        final List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(reservationDatabaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    void checkDateFromIsRequired() throws Exception {
        // Initialize database
        final int reservationDatabaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Set the field null
        reservation.setDateFrom(null);
        final ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // Create the reservation entity, which fails
        restReservationControllerMockMvc
                .perform(
                        post("/api/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(reservationDTO)))
                .andExpect(status().isBadRequest());

        final List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(reservationDatabaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateReservation() throws Exception {
        // Initialize database
        reservationRepository.saveAndFlush(reservation);

        final int reservationDatabaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation entity
        final Reservation updatedReservation =
                reservationRepository.findById(reservation.getId()).get();
        em.detach(updatedReservation);

        final ZonedDateTime updatedDateFrom = ZonedDateTime.now().plusDays(2);
        final ZonedDateTime updatedDateTo = ZonedDateTime.now().plusDays(3);

        updatedReservation.setDateFrom(updatedDateFrom);
        updatedReservation.setDateTo(updatedDateTo);

        final ReservationDTO reservationDTO = reservationMapper.toDto(updatedReservation);

        // Update the reservation entity
        restReservationControllerMockMvc
                .perform(
                        put("/api/reservations/{id}", updatedReservation.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(reservationDTO)))
                .andExpect(status().isOk());

        final List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(reservationDatabaseSizeBeforeUpdate);
        final Reservation testReservation = reservationList.get(0);
        assertThat(testReservation.getDateFrom()).isEqualTo(updatedDateFrom);
        assertThat(testReservation.getDateTo()).isEqualTo(updatedDateTo);
        assertThat(testReservation.getReservationStatus())
                .isEqualTo(reservation.getReservationStatus());
        assertThat(testReservation.getReservationType())
                .isEqualTo(reservation.getReservationType());
    }

    @Test
    @Transactional
    void getAllReservations() throws Exception {
        // Initialize database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservation entities
        restReservationControllerMockMvc
                .perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId())))
                .andExpect(
                        jsonPath("$.[*].dateFrom")
                                .value(hasItem(sameInstant(reservation.getDateFrom()))))
                .andExpect(
                        jsonPath("$.[*].dateTo")
                                .value(hasItem(sameInstant(reservation.getDateTo()))))
                .andExpect(
                        jsonPath("$.[*].reservationStatus")
                                .value(hasItem(reservation.getReservationStatus().toString())))
                .andExpect(
                        jsonPath("$.[*].reservationType")
                                .value(hasItem(reservation.getReservationType().toString())));
    }

    @Test
    @Transactional
    void getReservation() throws Exception {
        // Initialize database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation entity
        restReservationControllerMockMvc
                .perform(get("/api/reservations/{id}", reservation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(reservation.getId()))
                .andExpect(jsonPath("$.dateFrom").value(sameInstant(reservation.getDateFrom())))
                .andExpect(jsonPath("$.dateTo").value(sameInstant(reservation.getDateTo())))
                .andExpect(
                        jsonPath("$.reservationStatus")
                                .value(reservation.getReservationStatus().toString()))
                .andExpect(
                        jsonPath("$.reservationType")
                                .value(reservation.getReservationType().toString()));
    }

    @Test
    @Transactional
    void deleteReservation() throws Exception {
        // Initialize database
        reservationRepository.saveAndFlush(reservation);

        final int reservationDatabaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Delete the reservation entity
        restReservationControllerMockMvc
                .perform(
                        delete("/api/reservations/{id}", reservation.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(reservationRepository.findAll())
                .hasSize(reservationDatabaseSizeBeforeDelete - 1);
    }
}
