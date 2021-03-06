package com.innowisegroup.simpleblog.controller;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.model.UserRole;
import com.innowisegroup.simpleblog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(description = "Users REST controller")
@Validated
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
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Finds user by id",
            notes = "Provide an id to look up specific user",
            response = UserDto.class
    )
    public UserDto getUserById(
            @ApiParam("User's id") @PathVariable @Positive int id) throws ResponseStatusException {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(
            value = "Creates new user",
            notes = "Provide valid user data to create new user"
    )
    public void createUser(
            @ApiParam("User's photo") @RequestBody(required = false) MultipartFile photo,
            @ApiParam("User's name") @RequestParam String name,
            @ApiParam("User's lastname") @RequestParam String lastname,
            @ApiParam("User's email") @Email @RequestParam String email,
            @ApiParam("User's password") @Size(min = 8) @RequestParam String password,
            @ApiParam("User's role") @RequestParam UserRole role
    ) {
        try {
            UserDto userDto = new UserDto();
            userDto.setName(name);
            userDto.setLastname(lastname);
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setRole(role);
            userDto.setPhoto(photo.getBytes());
            userService.create(userDto);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(
            value = "Updates the user with given id",
            notes = "Provide user valid data as request body abd id as request param to update existing user"
    )
    public void updateUser(
            @ApiParam("User's id") @PathVariable @Positive int id,
            @ApiParam("User's photo") @RequestBody(required = false) MultipartFile photo,
            @ApiParam("User's name") @RequestParam String name,
            @ApiParam("User's lastname") @RequestParam String lastname,
            @ApiParam("User's email") @Email @RequestParam String email,
            @ApiParam("User's password") @Size(min = 8) @RequestParam String password,
            @ApiParam("User's role") @RequestParam UserRole role
            ) {
        try {
            UserDto userDto = new UserDto();
            userDto.setId(id);
            userDto.setName(name);
            userDto.setLastname(lastname);
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setRole(role);
            userDto.setPhoto(photo.getBytes());
            userService.updateById(id, userDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Deletes the user with given id",
            notes = "Provide an id to delete user"
    )
    public void deleteUser(
            @ApiParam("User's id") @PathVariable @Positive int id) {
        userService.deleteById(id);
    }

    @GetMapping("/passwords")
    @ApiOperation(
            value = "Gets passwords of all users",
            notes = "Retrieves all users passwords and returns them as a list",
            response = String.class,
            responseContainer = "List"
    )
    public List<String> getUsersPasswords() {
        return userService.getAllPasswords();
    }

    @GetMapping("/sorted/password/get/role/{role}")
    @ApiOperation(
            value = "Gets sorted by password user with specified role",
            notes = "Provide a role an you get users of the specified role which are sorted alphabetically by password as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getSortedByPasswordUsersByRole(
            @ApiParam("User's role") @PathVariable String role) {
        return userService.findSortedByPasswordUsersByRole(UserRole.valueOfLabel(role));
    }

    @GetMapping("/capitalized")
    @ApiOperation(
            value = "Gets all users capitalizing their names",
            notes = "Retrieves all users and returns them as a list after capitalizing their names",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersWithCapitalizedNames() {
        return userService.findAllWithCapitalizedNames();
    }

    @GetMapping("/ordered/lastname/desc")
    @ApiOperation(
            value = "Gets users descending ordered by last name",
            notes = "Retrieves all users in descending order by their last names and returns them as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersOrderByLastnameDesc() {
        return userService.findAllOrderedByLastnameDesc();
    }

    @GetMapping("/ordered/role")
    @ApiOperation(
            value = "Gets all users ordered alphabetically by role",
            notes = "Retrieves all users ordered alphabetically by role and returns them as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersOrderByRole() {
        return userService.findAllOrderedByRole();
    }

    @GetMapping("/name/{name}")
    @ApiOperation(
            value = "Gets users with the specified name",
            notes = "Provide a name and you get all users with such name as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByName(
            @ApiParam("User's name") @PathVariable String name) {
        return userService.findByName(name);
    }

    @GetMapping("/lastname/{lastname}")
    @ApiOperation(
            value = "Gets users with specified last name ordered by name",
            notes = "Provide a last name and you get users with such last name ordered alphabetically by name as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByLastnameOrderByName(
            @ApiParam("User's lastname") @PathVariable String lastname) {
        return userService.findByLastnameOrderedByName(lastname);
    }

    @GetMapping("/sorted/email/desc")
    @ApiOperation(
            value = "Gets all users sorted by email descending",
            notes = "Retrieves all users, sort them by email in a descending order and returns them as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getAllUsersSortedByEmailDescending() {
        return userService.findAllSortedByEmailDescending();
    }

    @GetMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation(
            value = "Gets users of specified page",
            notes = "Provide page num and size and you get users of such page as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByPageWithNumAndSize(
            @ApiParam("Page number which starts from 0") @PathVariable @PositiveOrZero int pageNum,
            @ApiParam("Size of the page") @PathVariable @Positive int pageSize) {
        return userService.findByPageWithNumAndSize(pageNum, pageSize);
    }

    @GetMapping("sorted/lastname/page/{pageSize}")
    @ApiOperation(
            value = "Gets sorted by last name users of 1st page with specified size",
            notes = "Provide page size and you get users of 1st page sorted by last name as a list",
            response = UserDto.class,
            responseContainer = "List"
    )
    public List<UserDto> getUsersByFirstPageWithSizeSortedByLastname(
            @ApiParam("Size of the page") @PathVariable @Positive int pageSize) {
        return userService.findByFirstPageWithSizeSortedByLastname(pageSize);
    }

    @GetMapping(value = "download/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(
            value = "Downloads photo of user by id",
            notes = "Provide user id to download his photo"
    )
    public ResponseEntity<Resource> downloadUserPhoto(@ApiParam("User's id") @PathVariable @Positive int id) {
        UserDto userDto = userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ByteArrayResource resource = new ByteArrayResource(userDto.getPhoto());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userDto.getName() + "_"
                + userDto.getLastname() + ".jpg\"")
                .body(resource);
    }
}
