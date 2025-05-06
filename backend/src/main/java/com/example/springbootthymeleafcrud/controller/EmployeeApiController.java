package com.example.springbootthymeleafcrud.controller;

import com.example.springbootthymeleafcrud.exception.ResourceNotFoundException;
import com.example.springbootthymeleafcrud.model.Employee;
import com.example.springbootthymeleafcrud.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeApiController {

    private final EmployeeRepository repository;

    public EmployeeApiController(EmployeeRepository repo) {
        this.repository = repo;
    }
    @GetMapping
    public ResponseEntity<Page<Employee>> listAll(Pageable pageable) {
        log.info("Fetching employees with pagination and sorting: {}", pageable);
        Page<Employee> employees = repository.findAll(pageable);
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        return repository.save(employee);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> createBulkEmployees(@RequestBody List<Employee> employees) {
        // Save the list of employees
        List<Employee> savedEmployees = repository.saveAll(employees);

        // Return the saved employees with 201 Created status
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployees);
    }
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        log.info("Fetching employee with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
        log.info("Updating employee with id: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        return repository.save(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        repository.delete(employee);
    }
}
