package com.example.springbootthymeleafcrud.controller;

import com.example.springbootthymeleafcrud.exception.ResourceNotFoundException;
import com.example.springbootthymeleafcrud.model.Employee;
import com.example.springbootthymeleafcrud.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeApiController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmployeeApiController.class);



    //    @Autowired
    private final EmployeeRepository repository;

    public EmployeeApiController(EmployeeRepository repo) {
        this.repository = repo;
    }
    @GetMapping
    public List<Employee> listAll() {
        log.info("Fetching all employees");
        return repository.findAll();
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        log.info("Creating new employee: {}", employee);
        return repository.save(employee);
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
