package com.gergelytamas.brdwksttest.service.mapper;

import com.gergelytamas.brdwksttest.domain.Reservation;
import com.gergelytamas.brdwksttest.service.dto.ReservationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring",
        uses = {UserMapper.class, CarMapper.class})
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "car.id", target = "carId")
    ReservationDTO toDto(Reservation reservation);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "modifiedOn", ignore = true)
    //    @Mapping(source = "userId", target = "user.id")
    //    @Mapping(source = "carId", target = "car.id")
    Reservation toEntity(ReservationDTO reservationDTO);
}
