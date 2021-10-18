package com.gergelytamas.brdwksttest.service.mapper;

import com.gergelytamas.brdwksttest.domain.User;
import com.gergelytamas.brdwksttest.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {ReservationMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User user);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "modifiedOn", ignore = true)
    User toEntity(UserDTO userDTO);
}
