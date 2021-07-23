package com.paypal.bfs.test.employeeserv.sevice.impl;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAppRuntimeException;
import com.paypal.bfs.test.employeeserv.model.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.util.EmployeeMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Override
    public Employee getEmployeeById(String id) {
        log.info("getEmployeeById method started with id " +id);
        Integer empId = checkEmpIdAndThrowException(id);
        Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(empId);
        if(employeeOptional.isPresent()){
            log.debug("employee object found with id " +id);
            return EmployeeMapperUtil.toEmployeePojoModel(employeeOptional.get());
        }else{
            log.error("no employee object found with id " +id);
            throw new EmployeeAppRuntimeException(HttpStatus.NOT_FOUND,"employee with given id is not found");
        }
    }

    @Override
    public Employee save(Employee employee) {
        log.info("save method execution started");
        if(null != employee.getId()) {
            Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(employee.getId());
            if (employeeOptional.isPresent()) {
                log.error("employee already exist with id " + employee.getId());
                throw new EmployeeAppRuntimeException(HttpStatus.CONFLICT, "an employee record  exists already with same id");
            }
        }
        try {
            EmployeeEntity employeeEntity = employeeRepository.save(EmployeeMapperUtil.toEmployeeEntity(employee));
            return EmployeeMapperUtil.toEmployeePojoModel(employeeEntity);
        } catch (Exception ex) {
            log.error("error occured while saving the record" + ex.getMessage());
            throw new EmployeeAppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "error occurred while saving the record " + ex.getMessage());
        }

    }

    private Integer checkEmpIdAndThrowException(String empId){
        try {
            return Integer.valueOf(empId);
        }
        catch(NumberFormatException ex){
            log.error("exception occured while converting the employee id to integer");
            return -1;
        }
    }
}
