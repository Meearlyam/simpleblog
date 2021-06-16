package com.innowisegroup.simpleblog.service.validation;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;

public interface UserValidationService {
    void validate(UserDto userDto) throws UserLastnameValidationException;
}
