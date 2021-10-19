package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank private String lastName;

    @Email
    private String email;

    @NotNull private ZonedDateTime birthDate;

    private String birthPlace;

    private String phoneNumber;

    private Set<Reservation> reservations = new HashSet<>();

    @NotNull private Boolean active;
}
