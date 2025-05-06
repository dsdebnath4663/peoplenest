package com.example.springbootthymeleafcrud.controller;

import com.example.springbootthymeleafcrud.dto.EmployeeCreateDTO;
import com.example.springbootthymeleafcrud.dto.EmployeeResponseDTO;
import com.example.springbootthymeleafcrud.enums.Status;
import com.example.springbootthymeleafcrud.exception.ResourceNotFoundException;
import com.example.springbootthymeleafcrud.model.Employee;
import com.example.springbootthymeleafcrud.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeApiController {

    private final EmployeeRepository repository;

    public EmployeeApiController(EmployeeRepository repo) {
        this.repository = repo;
    }

    @GetMapping
//    public ResponseEntity<Page<EmployeeResponseDTO>> listAll(Pageable pageable) {
    public ResponseEntity<Page<EmployeeResponseDTO>> listAll(@RequestParam(defaultValue = "ACTIVE") String status,
                                                             Pageable pageable) {

        log.info("Fetching employees with pagination and sorting: {}", pageable);
        Status enumStatus = Status.valueOf(status.toUpperCase());

        Page<Employee> employees = repository.findAllByStatus(pageable, enumStatus);

        Page<EmployeeResponseDTO> employeeDTOs = employees.map(employee ->
                new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail()));

        return ResponseEntity.ok(employeeDTOs);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeCreateDTO employeeDTO) {

        log.info("Creating new employee: {}", employeeDTO);
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());


        Employee savedEmployee = repository.save(employee);

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getEmail());


        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);


    }

    @PostMapping("/bulk")
//    public ResponseEntity<List<Employee>> createBulkEmployees(@RequestBody List<Employee> employees) {
    public ResponseEntity<List<EmployeeResponseDTO>> createBulkEmployees(@RequestBody List<EmployeeCreateDTO> employeeDTOs) {

        // Convert DTOs to entities and save them
        List<Employee> employees = employeeDTOs.stream()
//                .map(dto -> new Employee(dto.getName(), dto.getEmail()))
                .map(dto -> new Employee(dto.getName(), dto.getEmail(), Status.ACTIVE, dto.getDepartment()))

                .toList();

        // Save the list of employees
        List<Employee> savedEmployees = repository.saveAll(employees);


        // Convert saved entities to DTOs
        List<EmployeeResponseDTO> responseDTOs = savedEmployees.stream()
                .map(employee -> new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail()))
                .toList();
        // Return the saved employees with 201 Created status
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployee(@PathVariable Long id) {

        log.info("Fetching employee with id: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail());
        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/{id}")
//    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employeeDetails) {
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeCreateDTO employeeDTO) {

        log.info("Updating employee with id: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        Employee updatedEmployee = repository.save(employee);

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO(updatedEmployee.getId(), updatedEmployee.getName(), updatedEmployee.getEmail());


        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id,
                                               @RequestParam(defaultValue = "false") boolean hardDelete) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));


        if (hardDelete) {
            repository.delete(employee);
        } else {
            employee.setStatus(Status.INACTIVE);
            repository.save(employee);
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponseDTO>> searchEmployees(@RequestParam String query, Pageable pageable) {
        log.info("Searching employees with query: {}", query);
        Page<Employee> employees = repository.searchByNameOrEmailPaged(query, pageable);
        Page<EmployeeResponseDTO> responseDTOs = employees
                .map(employee -> new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail()));
        return ResponseEntity.ok(responseDTOs);

    }

    @GetMapping("/filter")
    public ResponseEntity<Page<EmployeeResponseDTO>> filterByDepartmentAndStatus(
            @RequestParam String department,
            @RequestParam Status status,
            Pageable pageable) {
        log.info("Filtering employees by department={} and status={}", department, status);

        Page<Employee> employees = repository.findByDepartmentIgnoreCaseAndStatus(department, status,pageable);

        Page<EmployeeResponseDTO> responseDTOs = employees
                .map(employee -> new EmployeeResponseDTO(employee.getId(), employee.getName(), employee.getEmail()));

        return ResponseEntity.ok(responseDTOs);
    }


}
