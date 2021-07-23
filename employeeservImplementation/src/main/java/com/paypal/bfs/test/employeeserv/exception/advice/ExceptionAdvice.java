package com.paypal.bfs.test.employeeserv.exception.advice;

import com.paypal.bfs.test.employeeserv.exception.EmployeeAppRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EmployeeAppRuntimeException.class)
    public ResponseEntity<String> handleException(EmployeeAppRuntimeException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}

