package com.gergelytamas.brdwksttest.domain;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "wkst_user", schema = "public")
@SequenceGenerator(
        name = "wkst_user_id_seq_gen",
        sequenceName = "wkst_user_id_seq",
        allocationSize = 1)
public class User extends BaseEntity implements Serializable {

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @NotBlank
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

    @Builder
    public User(
            final Integer id,
            final String firstName,
            final String lastName,
            final String email,
            final ZonedDateTime birthDate,
            final String birthPlace,
            final String phoneNumber,
            final Set<Reservation> reservations,
            final Boolean active,
            final ZonedDateTime createdOn,
            final ZonedDateTime modifiedOn) {
        super(id, createdOn, modifiedOn);
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
