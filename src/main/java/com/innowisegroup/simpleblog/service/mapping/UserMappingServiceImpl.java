package com.innowisegroup.simpleblog.service.mapping;

import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMappingServiceImpl implements UserMappingService {

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        return user;
    }
}
