package com.paypal.bfs.test.employeeserv.exception;

import org.springframework.http.HttpStatus;

public class EmployeeAppRuntimeException extends RuntimeException {

    private HttpStatus httpStatus;

    public EmployeeAppRuntimeException(HttpStatus httpStatus, Exception ex){
        super(ex);
        this.httpStatus = httpStatus;
    }
    public EmployeeAppRuntimeException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
