package com.innowisegroup.simpleblog.controller;

import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;
import com.innowisegroup.simpleblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void createUser(@RequestBody UserDto userDto) throws UserLastnameValidationException {
        userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/passwords")
    public List<String> getUsersPasswords() {
        return userService.getUsersPasswords();
    }

    @GetMapping(value = "/byRole/{role}")
    public List<UserDto> getSortedByNameUsersByRole(@PathVariable String role) {
        return userService.getSortedByNameUsersByRole(UserRole.valueOfLabel(role));
    }

    @GetMapping(value = "/capitalized")
    public List<UserDto> getUsersWithCapitalizedNames() {
        return userService.getUsersWithCapitalizedNames();
    }
}
