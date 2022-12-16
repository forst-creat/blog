package com.liuyang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author liuyang
 * @create 2022-08-11-0:57
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

}
