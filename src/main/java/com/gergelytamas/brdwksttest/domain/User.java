package com.gergelytamas.brdwksttest.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
@SequenceGenerator(name = "user_id_seq_gen", sequenceName = "user_id_seq", allocationSize = 1)
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private ZonedDateTime birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    public User(
            final Long id,
            final String firstName,
            final String lastName,
            final String email,
            final ZonedDateTime birthDate,
            final String birthPlace,
            final String phoneNumber,
            final HashSet<Reservation> reservations,
            final Boolean active) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.phoneNumber = phoneNumber;
        this.reservations = reservations;
        this.active = active;
    }
}
