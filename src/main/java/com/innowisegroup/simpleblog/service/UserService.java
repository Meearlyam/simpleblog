package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    void createUser(UserDto userDTO) throws UserLastnameValidationException;
    void updateUser(int id, UserDto userDTO);
    void deleteUser(int id);
    List<String> getUsersPasswords();
    List<UserDto> getSortedByNameUsersByRole(UserRole role);
    List<UserDto> getUsersWithCapitalizedNames();
}
