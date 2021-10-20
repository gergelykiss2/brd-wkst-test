package com.gergelytamas.brdwksttest.controller;

import com.gergelytamas.brdwksttest.service.CarService;
import com.gergelytamas.brdwksttest.service.dto.CarDTO;
import com.gergelytamas.brdwksttest.service.mapper.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/cars")
public class CarController {

    private final Logger log = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> save(@Valid @RequestBody final CarDTO carDTO)
            throws URISyntaxException {
        log.debug("REST request to save Car : {}", carDTO);

        if (carDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        final CarDTO result = carMapper.toDto(this.carService.save(carMapper.toEntity(carDTO)));
        return ResponseEntity.created(new URI("/api/cars/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> update(
            @Valid @RequestBody final CarDTO carDTO, @PathVariable final Integer id) {
        log.debug("REST request to update Car : {}", carDTO);

        if (carDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok()
                .body(carMapper.toDto(this.carService.update(carMapper.toEntity(carDTO), id)));
    }

    @GetMapping("")
    public ResponseEntity<List<CarDTO>> findAll() {
        log.debug("REST request to get all Cars");
        return ResponseEntity.ok()
                .body(
                        this.carService.findAll().stream()
                                .map(carMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> findById(@PathVariable final Integer id) {
        log.debug("REST request to get Car : {}", id);
        return ResponseEntity.of(this.carService.findById(id).map(carMapper::toDto));
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> findAllAvailable() {
        log.debug("REST request to get available Cars");
        return ResponseEntity.ok()
                .body(
                        this.carService.findAllAvailable().stream()
                                .map(carMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/by-make")
    public ResponseEntity<List<CarDTO>> findAllByMake(@RequestParam final String make) {
        log.debug("REST request to get all Cars by Make");
        return ResponseEntity.ok()
                .body(
                        this.carService.findAllByMake(make).stream()
                                .map(carMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/by-equipment")
    public ResponseEntity<List<CarDTO>> findAllByEquipment(@RequestParam final String equipment) {
        log.debug("REST request to get all Cars by Equipment");
        return ResponseEntity.ok()
                .body(
                        this.carService.findAllByEquipment(List.of(equipment)).stream()
                                .map(carMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable final Integer id) {
        log.debug("REST request to delete Car : {}", id);
        this.carService.delete(id);
        return ResponseEntity.noContent()
                .header("X-WkstTestApp-alert", "Car with id " + id + " has been deleted.")
                .build();
    }
}
