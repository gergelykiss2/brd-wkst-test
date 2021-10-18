package com.gergelytamas.brdwksttest.repository;

import com.gergelytamas.brdwksttest.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    //    @Query("select car from Car car left join fetch car.equipments where
    // car.equipments.description IN :equipment")
    //    List<Car> findAllByEquipment(@Param("equipment") final Collection<String> equipment);

    @Query("select car from Car car where car.make =: make")
    List<Car> findAllByMake(@Param("make") final String make);

    @Query("select car from Car car where car.carStatus = 'AVAILABLE'")
    List<Car> findAllAvailable();
}
