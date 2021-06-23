package com.innowisegroup.simpleblog.controller;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserValidationException;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "Users REST controller")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(
            value = "Gets all users",
            notes = "Retrieves all existing users as list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Finds user by id",
            notes = "Provide an id to look up specific user",
            response = UserDto.class
    )
    public UserDto getUserById(@ApiParam(required = true) @PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ApiOperation(
            value = "Creates new user",
            notes = "Provide valid user data to create new user"
    )
    public void createUser(@ApiParam(required = true) @RequestBody UserDto userDto) throws UserValidationException {
        try {
            userService.createUser(userDto);
        }
        catch (RuntimeException e) {
            throw new UserValidationException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(
            value = "Updates the user with given id",
            notes = "Provide user valid data as request body abd id as request param to update existing user"
    )
    public void updateUser(@ApiParam(required = true) @PathVariable int id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Deletes the user with given id",
            notes = "Provide an id to delete user"
    )
    public void deleteUser(@ApiParam(required = true) @PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping(value = "/passwords")
    @ApiOperation(
            value = "Gets passwords of all users",
            notes = "Retrieves all users passwords and returns them as a list",
            response = String.class,
            responseContainer = "List"
    )
    public List<String> getUsersPasswords() {
        return userService.getUsersPasswords();
    }

    @GetMapping(value = "/sorted/password/get/role/{role}")
    @ApiOperation(
            value = "Gets sorted by password user with specified role",
            notes = "Provide a role an you get users of the specified role which are sorted alphabetically by password as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getSortedByPasswordUsersByRole(@ApiParam(required = true) @PathVariable String role) {
        return userService.getSortedByPasswordUsersByRole(UserRole.valueOfLabel(role));
    }

    @GetMapping(value = "/capitalized")
    @ApiOperation(
            value = "Gets all users capitalizing their names",
            notes = "Retrieves all users and returns them as a list after capitalizing their names",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersWithCapitalizedNames() {
        return userService.getUsersWithCapitalizedNames();
    }

    @GetMapping(value = "/ordered/lastname/desc")
    @ApiOperation(
            value = "Gets users descending ordered by last name",
            notes = "Retrieves all users in descending order by their last names and returns them as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersOrderByLastnameDesc() {
        return userService.getUsersOrderByLastnameDesc();
    }

    @GetMapping(value = "/ordered/role")
    @ApiOperation(
            value = "Gets all users ordered alphabetically by role",
            notes = "Retrieves all users ordered alphabetically by role and returns them as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersOrderByRole() {
        return userService.getUsersOrderByRole();
    }

    @GetMapping(value = "/name/{name}")
    @ApiOperation(
            value = "Gets users with the specified name",
            notes = "Provide a name and you get all users with such name as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByName(@ApiParam(required = true) @PathVariable String name) {
        return userService.findUsersByName(name);
    }

    @GetMapping(value = "/lastname/{lastname}")
    @ApiOperation(
            value = "Gets users with specified last name ordered by name",
            notes = "Provide a last name and you get users with such last name ordered alphabetically by name as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByLastnameOrderByName(@ApiParam(required = true) @PathVariable String lastname) {
        return userService.findUsersByLastnameOrderByName(lastname);
    }
}
