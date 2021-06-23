package com.innowisegroup.simpleblog.service;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.model.User;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.repository.UserRepository;
import com.innowisegroup.simpleblog.service.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::convertToDto)
                .sorted(
                        Comparator.comparing(UserDto::getName, Comparator.nullsLast(Comparator.naturalOrder()))
                                .thenComparing(UserDto::getLastname, Comparator.nullsLast(Comparator.naturalOrder())
                                )
                )
                .collect(Collectors.toList());

    }

    @Override
    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(userMapper::convertToDto)
                .orElse(null);
    }

    @Override
    public void create(UserDto userDto) {
        userRepository.save(
                userMapper.convertToEntity(userDto)
        );
    }

    @Override
    public void updateById(long id, UserDto userDto) {
        userDto.setId(id);
        userRepository.save(
                userMapper.convertToEntity(
                        userDto
                )
        );
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<String> getAllPasswords() {
        return userRepository.findAll()
                .stream()
                .map(User::getPassword)
                .sorted(Comparator.nullsLast(
                        Comparator.comparing(Function.identity()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findSortedByPasswordUsersByRole(UserRole role) {
        return userRepository.findAll()
                .stream()
                // null check is the only possible solution?
                .filter(user -> user.getRole() != null
                        && user.getRole() == role
                        )
                .sorted(Comparator.comparing(User::getPassword))
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllWithCapitalizedNames() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getName() != null)
                .peek(user -> user.setName(String.valueOf(user.getName().charAt(0)).toUpperCase()
                        + user.getName().substring(1).toLowerCase())
                )
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllOrderedByLastnameDesc() {
        return userRepository.findByOrderByLastnameDesc()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllOrderedByRole() {
        return userRepository.findByOrderByRole()
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByLastnameOrderedByName(String lastname) {
        return userRepository.findByLastnameOrderByName(lastname)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllSortedByEmailDescending() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "email"))
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByPageWithNumAndSize(int pageNum, int pageSize) {
        Pageable definedPage = PageRequest.of(pageNum, pageSize);

        return userRepository.findAll(definedPage)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByFirstPageWithSizeSortedByLastname(int pageSize) {
        Pageable sortedByLastname = PageRequest.of(0, pageSize, Sort.by("lastname"));
        return userRepository.findAll(sortedByLastname)
                .stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
