package com.gergelytamas.brdwksttest.domain;

import com.gergelytamas.brdwksttest.domain.enumeration.CarStatus;
import com.gergelytamas.brdwksttest.domain.enumeration.FuelType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "car", schema = "public")
@SequenceGenerator(name = "car_id_seq_gen", sequenceName = "car_id_seq", allocationSize = 1)
public class Car extends BaseEntity implements Serializable {

    @NotBlank
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @NotBlank
    @Column(name = "make", nullable = false)
    private String make;

    @NotBlank
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_status", nullable = false)
    private CarStatus carStatus;

    @OneToMany(
            mappedBy = "car",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToMany(
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "car_equipment",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName = "id"))
    private Set<Equipment> equipments = new HashSet<>();

    @Builder
    public Car(
            final Integer id,
            final String licensePlate,
            final String make,
            final String model,
            final FuelType fuelType,
            final CarStatus carStatus,
            final Set<Reservation> reservations,
            final Set<Equipment> equipments,
            final ZonedDateTime createdOn,
            final ZonedDateTime modifiedOn) {
        super(id, createdOn, modifiedOn);
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.fuelType = fuelType;
        this.carStatus = carStatus;
        this.reservations = reservations;
        this.equipments = equipments;
    }
}
