package com.innowisegroup.simpleblog.service.mapping;

import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.dto.UserDto;

public interface UserMappingService {
    UserDto convertToDto(User user);
    User convertToEntity(UserDto userDTO);
}
