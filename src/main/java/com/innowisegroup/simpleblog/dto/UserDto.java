package com.innowisegroup.simpleblog.dto;

import com.innowisegroup.simpleblog.model.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@ApiModel(description = "Data transfer object that represents a user")
public class UserDto {
    @ApiModelProperty(notes = "The unique id of the user")
    private long id;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @ApiModelProperty(notes = "The user's name")
    private String name;

    @NotNull(message = "Lastname cannot be null")
    @NotBlank(message = "Lastname cannot be blank")
    @ApiModelProperty(notes = "The user's last name")
    private String lastname;

    @Email(message = "Email should be valid")
    @ApiModelProperty(notes = "The user's email")
    private String email;

    @Size(min = 8, message = "Password should not be shorter than 8 characters")
    @ApiModelProperty(notes = "The user's password")
    private String password;

    @ApiModelProperty(notes = "The user's role")
    private UserRole role;

    @ApiModelProperty(notes = "The user's photo (image file)")
    private byte[] photo;
}
