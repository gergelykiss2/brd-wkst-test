package com.gergelytamas.brdwksttest.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
public class Equipment extends BaseEntity implements Serializable {

    @NotNull
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "equipments")
    private Set<Car> cars = new HashSet<>();
}
