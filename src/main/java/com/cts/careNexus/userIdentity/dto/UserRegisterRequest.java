package com.cts.careNexus.userIdentity.dto;

import com.cts.careNexus.userIdentity.entities.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Phone number cannot be null")
    private Long phone;

    @NotNull(message = "Role is mandatory")
    private UserRole role;

    private Long departmentId;
}