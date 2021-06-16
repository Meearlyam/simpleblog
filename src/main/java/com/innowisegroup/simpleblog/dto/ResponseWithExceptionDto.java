package com.innowisegroup.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
//@NoArgsConstructor
//@Getter @Setter
@Data
public class ResponseWithExceptionDto {
    private String message;
    private Date date;
}
