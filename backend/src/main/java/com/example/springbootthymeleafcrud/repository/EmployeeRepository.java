package com.example.springbootthymeleafcrud.repository;

import com.example.springbootthymeleafcrud.enums.Status;
import com.example.springbootthymeleafcrud.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByStatus(Pageable pageable, Status enumStatus);
    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Employee> searchByNameOrEmailPaged(@Param("query") String query, Pageable pageable);

    Page<Employee> findByDepartmentIgnoreCaseAndStatus(String department, Status status, Pageable pageable);

}
