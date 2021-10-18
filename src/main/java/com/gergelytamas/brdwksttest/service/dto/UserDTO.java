package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Long id;

    @NotNull private String firstName;

    @NotNull private String lastName;

    @NotNull private String email;

    @NotNull private Instant birthDate;

    private String birthPlace;

    private String phoneNumber;

    private Set<Reservation> reservations = new HashSet<>();

    @NotNull private Boolean active;
}
