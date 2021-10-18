package com.gergelytamas.brdwksttest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date createdOn;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "modified_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date modifiedOn;

    @PrePersist
    public void prePersist() {
        this.createdOn = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedOn = new Date(System.currentTimeMillis());
    }
}
