package com.synchrony.assignment;

import com.synchrony.assignment.model.Employee;
import com.synchrony.assignment.repository.EmployeeRepository;
import com.synchrony.assignment.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ServiceSynchronyDbOpsApplicationTests {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setId(1);
        employee1.setName("John Doe");

        Employee employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("Jane Doe");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee result = employeeService.addEmployee(employee);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testUpdateEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(1);
        existingEmployee.setName("John Doe");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("Jane Doe");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Employee result = employeeService.updateEmployee(1, updatedEmployee);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById(1)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1);

        boolean result = employeeService.deleteEmployee(1);

        assertTrue(result);
    }
}
