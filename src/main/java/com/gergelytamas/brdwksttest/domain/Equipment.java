package com.gergelytamas.brdwksttest.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "equipment", schema = "public")
@SequenceGenerator(
        name = "equipment_id_seq_gen",
        sequenceName = "equipment_id_seq",
        allocationSize = 1)
public class Equipment extends BaseEntity implements Serializable {

    @NotBlank
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "equipments")
    private Set<Car> cars = new HashSet<>();

    @Builder
    public Equipment(
            final Integer id,
            final String description,
            final Set<Car> cars,
            final ZonedDateTime createdOn,
            final ZonedDateTime modifiedOn) {
        super(id, createdOn, modifiedOn);
        this.description = description;
        this.cars = cars;
    }
}
