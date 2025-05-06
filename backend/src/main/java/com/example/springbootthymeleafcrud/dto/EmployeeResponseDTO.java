package com.example.springbootthymeleafcrud.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;

    // Constructor for creating DTO from entity
    public EmployeeResponseDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
