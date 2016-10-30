package com.abc.web;

import com.abc.common.MyConstants;
import com.abc.dto.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

/**
 * as it is: global exception handler
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseData handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseData(MyConstants.CODE_ERR, "Constraint Violation！");
    }

}
