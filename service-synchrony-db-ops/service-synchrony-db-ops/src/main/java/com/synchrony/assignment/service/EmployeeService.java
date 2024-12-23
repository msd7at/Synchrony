package com.synchrony.assignment.service;

import com.synchrony.assignment.model.Employee;
import com.synchrony.assignment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Cacheable(value = "employee", key = "#id")
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Cacheable("employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @CacheEvict(value = {"employee", "employees"}, allEntries = true)
    @Transactional
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = {"employee", "employees"}, allEntries = true)
    @Transactional
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(updatedEmployee.getName());
            employee.setPosition(updatedEmployee.getPosition());
            employee.setSalary(updatedEmployee.getSalary());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found."));
    }

    @CacheEvict(value = {"employee", "employees"}, allEntries = true)
    @Transactional
    public boolean deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Async
    public CompletableFuture<Void> performConcurrentTask(Runnable task) {
        return CompletableFuture.runAsync(task);
    }
}