package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserValidationException;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(long id);
    void createUser(UserDto userDTO) throws UserValidationException;
    void updateUser(long id, UserDto userDTO);
    void deleteUser(long id);
    List<String> getUsersPasswords();
    List<UserDto> getSortedByPasswordUsersByRole(UserRole role);
    List<UserDto> getUsersWithCapitalizedNames();
    List<UserDto> getUsersOrderByLastnameDesc();
    List<UserDto> getUsersOrderByRole();
    List<UserDto> findUsersByName(String name);
    List<UserDto> findUsersByLastnameOrderByName(String lastname);
}
