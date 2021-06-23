package com.innowisegroup.simpleblog.dto;

import com.innowisegroup.simpleblog.model.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ApiModel(description = "Data transfer object that represents a user")
public class UserDto {
    @ApiModelProperty(notes = "The unique id of the user")
    private long id;

    @ApiModelProperty(notes = "The user's name")
    private String name;

    @ApiModelProperty(notes = "The user's last name")
    private String lastname;

    @ApiModelProperty(notes = "The user's email")
    private String email;

    @ApiModelProperty(notes = "The user's password")
    private String password;

    @ApiModelProperty(notes = "The user's role")
    private UserRole role;
}
