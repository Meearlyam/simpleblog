package com.innowisegroup.simpleblog.dto;

import com.innowisegroup.simpleblog.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private UserRole role;
}
