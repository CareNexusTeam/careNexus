package com.cts.carenexus.patientManagement.dto;

import com.cts.carenexus.patientManagement.entities.PatientStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Long patientId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String phone;
    private String email;
    private String address;
    private String emergencyContact;
    private Integer insuranceProviderId;
    private PatientStatus status;
}