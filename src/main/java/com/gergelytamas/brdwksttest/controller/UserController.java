package com.gergelytamas.brdwksttest.controller;

import com.gergelytamas.brdwksttest.service.UserService;
import com.gergelytamas.brdwksttest.service.dto.UserDTO;
import com.gergelytamas.brdwksttest.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(final UserService userService, final UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> save(@Valid @RequestBody final UserDTO userDTO)
            throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }

        final UserDTO result =
                userMapper.toDto(this.userService.save(userMapper.toEntity(userDTO)));
        return ResponseEntity.created(new URI("/api/users/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @Valid @RequestBody final UserDTO userDTO, @PathVariable final Integer id) {
        log.debug("REST request to update User : {}", userDTO);

        if (userDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .body(userMapper.toDto(this.userService.update(userMapper.toEntity(userDTO), id)));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> findAll() {
        log.debug("REST request to get all Users");
        return ResponseEntity.ok()
                .body(
                        this.userService.findAll().stream()
                                .map(userMapper::toDto)
                                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable final Integer id) {
        log.debug("REST request to get User : {}", id);
        return ResponseEntity.of(this.userService.findById(id).map(userMapper::toDto));
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> findByEmail(@RequestParam final String email) {
        log.debug("REST request to get User by email");
        return ResponseEntity.of(this.userService.findByEmail(email).map(userMapper::toDto));
    }

    @GetMapping("/birth-date")
    public ResponseEntity<UserDTO> findAllByMake(@RequestParam final Date birthDate) {
        log.debug("REST request to get User by birthDate");
        return ResponseEntity.of(
                this.userService.findByBirthDate(birthDate).map(userMapper::toDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable final Integer id) {
        log.debug("REST request to delete User : {}", id);
        this.userService.delete(id);
        return ResponseEntity.noContent()
                .header("X-WkstTestApp-alert", "User with id " + id + " has been deleted.")
                .build();
    }
}
