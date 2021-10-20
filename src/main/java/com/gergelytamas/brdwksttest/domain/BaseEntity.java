package com.gergelytamas.brdwksttest.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdOn;

    //    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "modified_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime modifiedOn;

    @PrePersist
    public void prePersist() {
        this.createdOn = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedOn = ZonedDateTime.now();
    }
}
