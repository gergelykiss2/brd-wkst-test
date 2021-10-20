package com.gergelytamas.brdwksttest.service.dto;

import com.gergelytamas.brdwksttest.domain.Reservation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO extends BaseDTO implements Serializable {

    @NotBlank private String firstName;

    @NotBlank private String lastName;

    @Email @NotBlank private String email;

    @NotNull private ZonedDateTime birthDate;

    private String birthPlace;

    private String phoneNumber;

    private Set<Reservation> reservations = new HashSet<>();

    @NotNull private Boolean active;
}
