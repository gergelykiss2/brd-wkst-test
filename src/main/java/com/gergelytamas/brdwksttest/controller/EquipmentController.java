package com.gergelytamas.brdwksttest.controller;

import com.gergelytamas.brdwksttest.service.EquipmentService;
import com.gergelytamas.brdwksttest.service.dto.EquipmentDTO;
import com.gergelytamas.brdwksttest.service.mapper.EquipmentMapper;
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
@RequestMapping("/api/equipments")
public class EquipmentController {

    private final Logger log = LoggerFactory.getLogger(EquipmentController.class);

    private final EquipmentService equipmentService;

    private final EquipmentMapper equipmentMapper;

    public EquipmentController(
            final EquipmentService equipmentService, final EquipmentMapper equipmentMapper) {
        this.equipmentService = equipmentService;
        this.equipmentMapper = equipmentMapper;
    }

    @PostMapping("")
    public ResponseEntity<EquipmentDTO> save(@Valid @RequestBody final EquipmentDTO equipmentDTO)
            throws URISyntaxException {
        log.debug("REST request to save Equipment : {}", equipmentDTO);

        if (equipmentDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        final EquipmentDTO result =
                equipmentMapper.toDto(
                        this.equipmentService.save(equipmentMapper.toEntity(equipmentDTO)));
        return ResponseEntity.created(new URI("/api/equipments/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> update(
            @Valid @RequestBody final EquipmentDTO equipmentDTO, @PathVariable final Integer id) {
        log.debug("REST request to update Equipment : {}", equipmentDTO);

        if (equipmentDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .body(
                        equipmentMapper.toDto(
                                this.equipmentService.update(
                                        equipmentMapper.toEntity(equipmentDTO), id)));
    }

    @GetMapping("")
    public ResponseEntity<List<EquipmentDTO>> findAll() {
        log.debug("REST request to get all Equipments");
        return ResponseEntity.ok()
                .body(
                        this.equipmentService.findAll().stream()
                                .map(equipmentMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> findById(@PathVariable final Integer id) {
        log.debug("REST request to get Equipment : {}", id);
        return ResponseEntity.of(this.equipmentService.findById(id).map(equipmentMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable final Integer id) {
        log.debug("REST request to delete Equipment : {}", id);
        this.equipmentService.delete(id);
        return ResponseEntity.noContent()
                .header("X-WkstTestApp-alert", "Equipment with id " + id + " has been deleted.")
                .build();
    }
}
