package com.vldby.demochat.rest.mapper;

import com.vldby.demochat.entity.User;
import com.vldby.demochat.rest.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto dto);
}
