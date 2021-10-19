package com.gergelytamas.brdwksttest.controller;

import com.gergelytamas.brdwksttest.domain.enumeration.ReservationStatus;
import com.gergelytamas.brdwksttest.service.ReservationService;
import com.gergelytamas.brdwksttest.service.dto.ReservationDTO;
import com.gergelytamas.brdwksttest.service.mapper.ReservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    private final ReservationMapper reservationMapper;

    public ReservationController(
            ReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @PostMapping("")
    public ResponseEntity<ReservationDTO> save(
            @Valid @RequestBody final ReservationDTO reservationDTO) throws URISyntaxException {
        log.debug("REST request to save Reservation : {}", reservationDTO);

        if (reservationDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final ReservationDTO result =
                reservationMapper.toDto(
                        this.reservationService.save(reservationMapper.toEntity(reservationDTO)));
        return ResponseEntity.created(new URI("/api/reservations/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> update(
            @Valid @RequestBody final ReservationDTO reservationDTO, @PathVariable final Long id) {
        log.debug("REST request to update Reservation : {}", reservationDTO);

        if (reservationDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .body(
                        reservationMapper.toDto(
                                this.reservationService.update(
                                        reservationMapper.toEntity(reservationDTO), id)));
    }

    @GetMapping("")
    public ResponseEntity<List<ReservationDTO>> findAll() {
        log.debug("REST request to get all Reservations");
        return ResponseEntity.ok()
                .body(
                        this.reservationService.findAll().stream()
                                .map(reservationMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> findById(@PathVariable final Long id) {
        log.debug("REST request to get Reservation : {}", id);
        return ResponseEntity.of(
                this.reservationService.findById(id).map(reservationMapper::toDto));
    }

    @GetMapping("/status")
    public ResponseEntity<List<ReservationDTO>> findByReservationStatus(
            @RequestParam final ReservationStatus reservationStatus) {
        log.debug("REST request to get Reservation by status: {}", reservationStatus);
        return ResponseEntity.ok()
                .body(
                        this.reservationService
                                .findAllByReservationStatus(reservationStatus)
                                .stream()
                                .map(reservationMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/user")
    public ResponseEntity<ReservationDTO> findByUser(@RequestParam final Long userId) {
        log.debug("REST request to get Reservation by userId: {}", userId);
        return ResponseEntity.of(
                this.reservationService.findByUser(userId).map(reservationMapper::toDto));
    }

    @GetMapping("/car")
    public ResponseEntity<ReservationDTO> findByCar(@PathVariable final Long carId) {
        log.debug("REST request to get Reservation by carId: {}", carId);
        return ResponseEntity.of(
                this.reservationService.findByCar(carId).map(reservationMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable final Long id) {
        log.debug("REST request to delete Reservation : {}", id);
        this.reservationService.delete(id);
        return ResponseEntity.noContent()
                .header("X-WkstTestApp-alert", "Reservation with id " + id + " has been deleted.")
                .build();
    }
}
