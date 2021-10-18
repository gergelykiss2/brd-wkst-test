package com.gergelytamas.brdwksttest.service.mapper;

import com.gergelytamas.brdwksttest.domain.Equipment;
import com.gergelytamas.brdwksttest.service.dto.EquipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {CarMapper.class})
public interface EquipmentMapper extends EntityMapper<EquipmentDTO, Equipment> {

    EquipmentDTO toDto(Equipment equipment);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "modifiedOn", ignore = true)
    Equipment toEntity(EquipmentDTO equipmentDTO);
}
