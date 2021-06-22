package com.innowisegroup.simpleblog.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if(Objects.isNull(role)) {
            return null;
        }
        return role.getLabel();
    }

    @Override
    public UserRole convertToEntityAttribute(String label) {
        if(label == null) {
            return null;
        }
        return Stream.of(UserRole.values())
                .filter(userRole -> userRole.getLabel().equals(label.toLowerCase(Locale.ROOT)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
