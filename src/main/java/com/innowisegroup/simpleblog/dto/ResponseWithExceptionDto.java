package com.innowisegroup.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ResponseWithExceptionDto {
    private String cause;
    private String message;
    private Date date;
}
