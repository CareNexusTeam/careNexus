package com.carenexus.carenexus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PatientRegisterRequest {

    @NotBlank(message = "Patient name is mandatory")
    private String name;

    @NotNull(message = "Date of Birth is mandatory")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is mandatory")
    private String gender;

    private String bloodGroup;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @Email(message = "Provide a valid email address")
    private String email;

    private String address;
    private String emergencyContact;
    private Integer insuranceProviderId;
}