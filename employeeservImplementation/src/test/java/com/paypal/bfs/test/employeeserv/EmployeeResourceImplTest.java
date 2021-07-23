package com.paypal.bfs.test.employeeserv;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAppRuntimeException;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import java.util.Date;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeResourceImpl.class)
public class EmployeeResourceImplTest {


    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testfindGetById() throws Exception {
        Employee employee = new Employee();
        setEmployeeProperties(employee);

        Mockito.when(employeeService.getEmployeeById("1")).thenReturn(employee);

        mockMvc.perform(get("/v1/bfs/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", Matchers.is("Lokesh")))
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void testfindGetByIdNotFound() throws Exception {

        Mockito.when(employeeService.getEmployeeById("23")).thenThrow(new EmployeeAppRuntimeException(HttpStatus.NOT_FOUND, "no record found"));
        mockMvc.perform(get("/v1/bfs/employees/23"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        setEmployeeProperties(employee);
        Mockito.when(employeeService.save(employee)).thenReturn(employee);
        ObjectMapper obj = new ObjectMapper();
        mockMvc.perform(post("/v1/bfs/employees").contentType("application/json").content(obj.writeValueAsString(employee)))
                .andExpect(status().isCreated());
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
