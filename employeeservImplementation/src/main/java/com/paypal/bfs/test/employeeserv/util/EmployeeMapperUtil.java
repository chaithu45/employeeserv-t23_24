package com.paypal.bfs.test.employeeserv.util;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.model.EmployeeEntity;

public class EmployeeMapperUtil {

    private EmployeeMapperUtil() {

    }

    public static EmployeeEntity toEmployeeEntity(Employee employee) {
        EmployeeEntity emp = new EmployeeEntity();
        if(null != employee.getId()) {
            emp.setId(employee.getId());
        }
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setDateOfBirth(employee.getDateOfBirth());
        Address address = employee.getAddress();
        if (null != address) {
            emp.setAddressLine1(address.getLine1());
            emp.setAddressLine2(address.getLine2());
            emp.setCity(address.getCity());
            emp.setState(address.getState());
            emp.setCountry(address.getCountry());
            emp.setZipCode(address.getZipCode());
        }
        return emp;
    }

    public static Employee toEmployeePojoModel(EmployeeEntity entity) {
        Employee emp = new Employee();
        emp.setId(entity.getId());
        emp.setFirstName(entity.getFirstName());
        emp.setLastName(entity.getLastName());
        emp.setDateOfBirth(entity.getDateOfBirth());
        Address address = new Address();
        address.setLine1(entity.getAddressLine1());
        address.setLine2(entity.getAddressLine2());
        address.setCity(entity.getCity());
        address.setCountry(entity.getCountry());
        address.setZipCode(entity.getZipCode());
        address.setState(entity.getState());
        emp.setAddress(address);
        return emp;
    }
}
