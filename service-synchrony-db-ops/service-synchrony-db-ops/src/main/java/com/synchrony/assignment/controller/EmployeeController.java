package com.synchrony.assignment.controller;

import com.synchrony.assignment.model.Employee;
import com.synchrony.assignment.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        logger.debug("Request Received GET ID"+ id);
        logger.info("Request Received to GET data for ID"+ id);
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(404).body("Employee not found.");
            }
        } catch (Exception e) {
            logger.error("Error Received while fetching from DB "+ e.getMessage());
            return ResponseEntity.status(500).body("Error retrieving employee: " + e.getMessage());
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error Received while fetching from DB "+ e.getMessage());
            return ResponseEntity.status(500).body("Error retrieving employees: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        String employeeId = String.valueOf(employee == null ? null : employee.getId());
        logger.info("Request Received to GET data for ID"+ employeeId);
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            logger.error("Error Received while fetching from DB "+ e.getMessage());
            return ResponseEntity.status(500).body("Error adding employee: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        logger.info("Request Received to update data for ID"+ id);
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error Received while fetching from DB "+ e.getMessage());
            return ResponseEntity.status(500).body("Error updating employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        logger.info("Request Received to Delete data for ID"+ id);
        try {
            boolean isDeleted = employeeService.deleteEmployee(id);
            if (isDeleted) {
                return ResponseEntity.ok("Employee deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Employee not found.");
            }
        } catch (Exception e) {
            logger.error("Error Received while fetching from DB "+ e.getMessage());
            return ResponseEntity.status(500).body("Error deleting employee: " + e.getMessage());
        }
    }
}
