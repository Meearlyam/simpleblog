package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();
    Optional<UserDto> findById(long id);
    void create(UserDto userDTO);
    void updateById(long id, UserDto userDTO);
    void deleteById(long id);

    List<String> getAllPasswords();
    List<UserDto> findSortedByPasswordUsersByRole(UserRole role);
    List<UserDto> findAllWithCapitalizedNames();
    List<UserDto> findAllOrderedByLastnameDesc();
    List<UserDto> findAllOrderedByRole();
    List<UserDto> findByName(String name);
    List<UserDto> findByLastnameOrderedByName(String lastname);

    List<UserDto> findAllSortedByEmailDescending();
    List<UserDto> findByPageWithNumAndSize(int pageNum, int pageSize);
    List<UserDto> findByFirstPageWithSizeSortedByLastname(int pageSize);
}
