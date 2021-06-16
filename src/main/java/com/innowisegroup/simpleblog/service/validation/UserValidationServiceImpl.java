package com.innowisegroup.simpleblog.service.validation;

import com.innowisegroup.simpleblog.dto.UserDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserValidationServiceImpl implements UserValidationService {

    @Override
    public void validate(UserDto userDto) throws UserLastnameValidationException {
        if(isLastnameInvalid(userDto.getLastname())) {
            throw new UserLastnameValidationException("User lastname is invalid!");
        }
    }

    private boolean isLastnameInvalid(String lastname) {
        return isLastnameNull(lastname) || isLastnameEmpty(lastname);
    }

    private boolean isLastnameNull(String lastname) {
        return Objects.isNull(lastname);
    }

    private boolean isLastnameEmpty(String lastname) {

        return lastname.replaceAll("\\s|\\n", "").isEmpty();
    }
}
