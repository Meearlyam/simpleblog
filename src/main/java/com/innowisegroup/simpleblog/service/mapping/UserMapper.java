package com.innowisegroup.simpleblog.service.mapping;

import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto convertToDto(User user);
    User convertToEntity(UserDto userDTO);
}
