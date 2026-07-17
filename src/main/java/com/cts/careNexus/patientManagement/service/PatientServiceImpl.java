package com.cts.careNexus.patientManagement.service;

import com.cts.careNexus.patientManagement.dto.PatientDto;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.entities.PatientStatus;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo patientRepo;

    // Converts the DTO to an entity, sets the default status to Active if null, and saves the new patient.
    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = mapToEntity(patientDto);
        if (patient.getStatus() == null) {
            patient.setStatus(PatientStatus.Active);
        }
        Patient savedPatient = patientRepo.save(patient);
        return mapToDto(savedPatient);
    }

    // Fetches all patient records from the database and maps them into a list of DTOs.
    @Override
    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients() {
        return patientRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Retrieves a specific patient by their ID or throws an exception if the record does not exist.
    @Override
    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        return mapToDto(patient);
    }

    // Finds the patient, updates their provided details conditionally, and saves the changes.
    @Override
    @Transactional
    public PatientDto updatePatientStatus(PatientDto patientDto, Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        if (patientDto.getStatus() != null) patient.setStatus(patientDto.getStatus());
        if (patientDto.getName() != null) patient.setName(patientDto.getName());
        if (patientDto.getPhone() != null) patient.setPhone(patientDto.getPhone());
        if (patientDto.getEmail() != null) patient.setEmail(patientDto.getEmail());
        if (patientDto.getAddress() != null) patient.setAddress(patientDto.getAddress());

        Patient updatedPatient = patientRepo.save(patient);
        return mapToDto(updatedPatient);
    }

    // Verifies the existence of a patient by their ID and deletes their record from the database.
    @Override
    @Transactional
    public void deletePatient(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        patientRepo.delete(patient);
    }

    // Transforms a Patient database entity object into a PatientDto data transfer object.
    private PatientDto mapToDto(Patient patient) {
        PatientDto dto = new PatientDto();
        dto.setPatientId(patient.getPatientId());
        dto.setName(patient.getName());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setPhone(patient.getPhone());
        dto.setEmail(patient.getEmail());
        dto.setAddress(patient.getAddress());
        dto.setEmergencyContact(patient.getEmergencyContact());
        dto.setInsuranceProviderId(patient.getInsuranceProviderId());
        dto.setStatus(patient.getStatus());
        return dto;
    }

    // Transforms a PatientDto data transfer object into a Patient database entity object.
    private Patient mapToEntity(PatientDto dto) {
        Patient patient = new Patient();
        patient.setPatientId(dto.getPatientId());
        patient.setName(dto.getName());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setAddress(dto.getAddress());
        patient.setEmergencyContact(dto.getEmergencyContact());
        patient.setInsuranceProviderId(dto.getInsuranceProviderId());
        patient.setStatus(dto.getStatus());
        return patient;
    }
}