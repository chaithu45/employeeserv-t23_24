package com.paypal.bfs.test.employeeserv.service.impl;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAppRuntimeException;
import com.paypal.bfs.test.employeeserv.model.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.sevice.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceImplTest {
    @InjectMocks
    EmployeeServiceImpl service;

    @Mock
    EmployeeRepository dao;

    @Test
    public void testGetEmployeeById()
    {
        EmployeeEntity employee = new EmployeeEntity();
        setEmployeeProperties(employee);
        when(dao.findById(1)).thenReturn(java.util.Optional.of(employee));
        Employee emp = service.getEmployeeById("1");
        assertEquals(1,emp.getId().intValue());
        verify(dao, times(1)).findById(Mockito.anyInt());
    }

    @Test
    public void testGetEmployeeByIdNotFound()
    {

        when(dao.findById(1)).thenReturn(Optional.empty());
        assertThrows(EmployeeAppRuntimeException.class,() -> service.getEmployeeById("123"));
        verify(dao, times(1)).findById(Mockito.anyInt());
    }
    @Test
    public void testGetEmployeeByIdWithInvalidId()
    {
        assertThrows(EmployeeAppRuntimeException.class,() -> service.getEmployeeById("abc123"));
    }


    @Test
    public void testSaveEmployee()
    {
        Employee employee = new Employee();
        setEmployeeProperties(employee);
        EmployeeEntity entity = new EmployeeEntity();
        setEmployeeProperties(entity);
        when(dao.findById(Mockito.any())).thenReturn(Optional.empty());
        when(dao.save(Mockito.any())).thenReturn(entity);
        Employee emp = service.save(employee);
        assertEquals(1,emp.getId().intValue());
        verify(dao, times(1)).save(Mockito.any());
    }
    @Test
    public void testSaveEmployeeWithSameId()
    {
        Employee employee = new Employee();
        setEmployeeProperties(employee);
        EmployeeEntity entity = new EmployeeEntity();
        setEmployeeProperties(entity);
        when(dao.findById(Mockito.any())).thenReturn(Optional.of(entity));
        assertThrows(EmployeeAppRuntimeException.class,() -> service.save(employee));
        verify(dao, times(1)).findById(Mockito.anyInt());
    }


    private void setEmployeeProperties(EmployeeEntity employee) {
        employee.setId(1);
        employee.setFirstName("Lokesh");
        employee.setLastName("Kumar");
        employee.setDateOfBirth(new Date());
        employee.setAddressLine1("street 1");
        employee.setCity("Bangalore");
        employee.setCountry("India");
        employee.setState("KA");
        employee.setZipCode("12345");

    }
    private void setEmployeeProperties(Employee employee) {
        employee.setId(1);
        employee.setFirstName("Lokesh");
        employee.setLastName("Kumar");
        employee.setDateOfBirth(new Date());
        Address ad = new Address();
        ad.setLine1("street 1");
        ad.setCity("Bangalore");
        ad.setCountry("India");
        ad.setState("KA");
        ad.setZipCode("12345");
        employee.setAddress(ad);
    }
}
