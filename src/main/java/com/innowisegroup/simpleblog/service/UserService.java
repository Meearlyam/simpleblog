package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);
    void createUser(UserDto userDTO) throws UserLastnameValidationException;
    void updateUser(long id, UserDto userDTO);
    void deleteUser(long id);
    List<String> getUsersPasswords();
    List<UserDto> getSortedByNameUsersByRole(UserRole role);
    List<UserDto> getUsersWithCapitalizedNames();
}
