package com.cts.careNexus.patientManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Blood_Group")
    private String bloodGroup;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "Emergency_Contact")
    private String emergencyContact;

    @Column(name = "InsuranceProvider_ID")
    private Integer insuranceProviderId;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private PatientStatus status;
}