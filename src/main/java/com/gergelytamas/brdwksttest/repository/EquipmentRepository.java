package com.gergelytamas.brdwksttest.repository;

import com.gergelytamas.brdwksttest.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @Query("select equipment from Equipment equipment where equipment.description =: description")
    Optional<Equipment> findByDescription(@Param("description") final String description);
}
