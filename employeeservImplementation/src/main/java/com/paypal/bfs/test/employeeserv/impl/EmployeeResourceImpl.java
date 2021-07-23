package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Implementation class for employee resource.
 */
@RestController
@Slf4j
public class EmployeeResourceImpl implements EmployeeResource {

    private EmployeeService employeeService;

    public EmployeeResourceImpl(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        log.info("employeeGetById method started " +id);
        Employee employee = employeeService.getEmployeeById(id);
        log.info("employeeGetById method ended " +id);
        return new ResponseEntity<>(employee, HttpStatus.OK);


    }

    @Override
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        log.info("createEmployee method started");
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);

    }
}
