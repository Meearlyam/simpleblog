package com.innowisegroup.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@AllArgsConstructor
@Data
public class ResponseWithExceptionDto {
    private String cause;
    private Map<String, String> violations;
    private Date date;
}
