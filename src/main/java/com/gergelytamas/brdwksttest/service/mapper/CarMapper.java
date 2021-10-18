package com.gergelytamas.brdwksttest.service.mapper;

import com.gergelytamas.brdwksttest.domain.Car;
import com.gergelytamas.brdwksttest.service.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ReservationMapper.class})
public interface CarMapper extends EntityMapper<CarDTO, Car> {

    CarDTO toDto(Car car);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "modifiedOn", ignore = true)
    Car toEntity(CarDTO carDTO);
}
