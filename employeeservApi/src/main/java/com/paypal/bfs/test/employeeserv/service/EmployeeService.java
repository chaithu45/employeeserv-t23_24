package com.paypal.bfs.test.employeeserv.service;


import com.paypal.bfs.test.employeeserv.api.model.Employee;

public interface EmployeeService {
    Employee getEmployeeById(String id);

    Employee save(Employee employee);
}
