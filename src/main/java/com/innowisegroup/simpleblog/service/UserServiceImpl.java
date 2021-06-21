package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;
import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.repository.UserRepository;
import com.innowisegroup.simpleblog.service.mapping.UserMappingService;
import com.innowisegroup.simpleblog.service.validation.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMappingService userMappingService;
    private final UserValidationService userValidationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMappingService userMappingService,
                           UserValidationService userValidationService) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
        this.userValidationService = userValidationService;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMappingService::convertToDto)
                .sorted(
                        Comparator.comparing(UserDto::getName, Comparator.nullsLast(Comparator.naturalOrder()))
                                .thenComparing(UserDto::getLastname, Comparator.nullsLast(Comparator.naturalOrder())
                                )
                )
                .collect(Collectors.toList());

    }

    @Override
    public UserDto getUserById(long id) {
        return userRepository.findById(id)
                .map(userMappingService::convertToDto)
                .orElse(null);
    }

    @Override
    public void createUser(UserDto userDto) throws UserLastnameValidationException {
        userValidationService.validate(userDto);
        userRepository.save(
                userMappingService.convertToEntity(userDto)
        );
    }

    @Override
    public void updateUser(long id, UserDto userDto) {
        userDto.setId(id);
        userRepository.save(
                userMappingService.convertToEntity(
                        userDto
                )
        );
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<String> getUsersPasswords() {
        return userRepository.findAll()
                .stream()
                .map(User::getPassword)
                .sorted(Comparator.nullsLast(
                        Comparator.comparing(Function.identity()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getSortedByPasswordUsersByRole(UserRole role) {
        return userRepository.findAll()
                .stream()
                // null check is the only possible solution?
                .filter(user -> user.getRole() != null
                        && user.getRole() == role
                        )
                .sorted(Comparator.comparing(User::getPassword))
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersWithCapitalizedNames() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getName() != null)
                .peek(user -> user.setName(String.valueOf(user.getName().charAt(0)).toUpperCase()
                        + user.getName().substring(1).toLowerCase())
                )
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersOrderByLastnameDesc() {
        return userRepository.findByOrderByLastnameDesc()
                .stream()
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersOrderByRole() {
        return userRepository.findByOrderByRole()
                .stream()
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findUsersByLastnameOrderByName(String lastname) {
        return userRepository.findByLastnameOrderByName(lastname)
                .stream()
                .map(userMappingService::convertToDto)
                .collect(Collectors.toList());
    }
}
