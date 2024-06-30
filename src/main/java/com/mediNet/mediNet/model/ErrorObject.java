package com.mediNet.mediNet.model;

import lombok.Data;

import java.sql.Date;

@Data
public class ErrorObject {

    private Integer statusCode;

    private String message;

    private Date timestamp;
}
