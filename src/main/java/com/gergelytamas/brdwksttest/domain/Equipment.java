package com.gergelytamas.brdwksttest.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "equipment")
@SequenceGenerator(
        name = "equipment_id_seq_gen",
        sequenceName = "equipment_id_seq",
        allocationSize = 1)
@NoArgsConstructor
public class Equipment extends BaseEntity implements Serializable {

    @NotBlank
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "equipments")
    private Set<Car> cars = new HashSet<>();

    public Equipment(final Long id, final String description, final Set<Car> cars) {
        super();
        this.description = description;
        this.cars = cars;
    }
}
