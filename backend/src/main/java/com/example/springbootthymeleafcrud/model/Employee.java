package com.example.springbootthymeleafcrud.model;

import com.example.springbootthymeleafcrud.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
//@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Represents the current status of the employee.
     *
     * The @Enumerated(EnumType.STRING) annotation ensures that the enum value
     * is stored in the database as a string (e.g., "ACTIVE", "INACTIVE") rather than
     * its ordinal number (e.g., 0, 1). This approach improves database readability and
     * prevents issues if the order of the enum constants changes in the future.
     *
     * Example:
     *   Status.ACTIVE   -> stored as "ACTIVE"
     *   Status.INACTIVE -> stored as "INACTIVE"
     */
    @Enumerated(EnumType.STRING)
    private Status status;


    public  Employee(){

    }
    public Employee(String name, String email) {
        this.name=name;
        this.email=email;
        this.status = Status.ACTIVE;

    }
}
