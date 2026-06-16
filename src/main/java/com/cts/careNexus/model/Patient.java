package com.carenexus.carenexus.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientID")
    private int patientId;

    @Column(name = "Name", length = 100, nullable = false)
    private String name;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "Gender", length = 20)
    private String gender;

    @Column(name = "BloodGroup", length = 5)
    private String bloodGroup;

    @Column(name = "Phone", length = 15)
    private String phone;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "EmergencyContact", length = 50)
    private String emergencyContact;

    @Column(name = "InsuranceProviderID")
    private Integer insuranceProviderId;

    @Column(name = "Status", length = 20)
    private String status = "Active";
}