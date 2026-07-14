package com.cts.careNexus.patientManagement.service;

import com.cts.careNexus.patientManagement.dto.PatientDto;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.entities.PatientStatus;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {

        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setGender(patientDto.getGender());
        patient.setBloodGroup(patientDto.getBloodGroup());
        patient.setPhone(patientDto.getPhone());
        patient.setEmail(patientDto.getEmail());
        patient.setAddress(patientDto.getAddress());
        patient.setEmergencyContact(patientDto.getEmergencyContact());
        patient.setInsuranceProviderId(patientDto.getInsuranceProviderId());

        if (patientDto.getStatus() == null) {
            patient.setStatus(PatientStatus.Active);
        } else {
            patient.setStatus(patientDto.getStatus());
        }

        Patient savedPatient = patientRepo.save(patient);

        PatientDto responseDto = new PatientDto();
        responseDto.setPatientId(savedPatient.getPatientId());
        responseDto.setName(savedPatient.getName());
        responseDto.setDateOfBirth(savedPatient.getDateOfBirth());
        responseDto.setGender(savedPatient.getGender());
        responseDto.setBloodGroup(savedPatient.getBloodGroup());
        responseDto.setPhone(savedPatient.getPhone());
        responseDto.setEmail(savedPatient.getEmail());
        responseDto.setAddress(savedPatient.getAddress());
        responseDto.setEmergencyContact(savedPatient.getEmergencyContact());
        responseDto.setInsuranceProviderId(savedPatient.getInsuranceProviderId());
        responseDto.setStatus(savedPatient.getStatus());

        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDto> getAllPatients() {
        List<Patient> patients = patientRepo.findAll();
        List<PatientDto> dtoList = new ArrayList<>();

        for (Patient p : patients) {
            PatientDto dto = new PatientDto();
            dto.setPatientId(p.getPatientId());
            dto.setName(p.getName());
            dto.setDateOfBirth(p.getDateOfBirth());
            dto.setGender(p.getGender());
            dto.setBloodGroup(p.getBloodGroup());
            dto.setPhone(p.getPhone());
            dto.setEmail(p.getEmail());
            dto.setAddress(p.getAddress());
            dto.setEmergencyContact(p.getEmergencyContact());
            dto.setInsuranceProviderId(p.getInsuranceProviderId());
            dto.setStatus(p.getStatus());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDto getPatientById(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

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

        PatientDto responseDto = new PatientDto();
        responseDto.setPatientId(updatedPatient.getPatientId());
        responseDto.setName(updatedPatient.getName());
        responseDto.setDateOfBirth(updatedPatient.getDateOfBirth());
        responseDto.setGender(updatedPatient.getGender());
        responseDto.setBloodGroup(updatedPatient.getBloodGroup());
        responseDto.setPhone(updatedPatient.getPhone());
        responseDto.setEmail(updatedPatient.getEmail());
        responseDto.setAddress(updatedPatient.getAddress());
        responseDto.setEmergencyContact(updatedPatient.getEmergencyContact());
        responseDto.setInsuranceProviderId(updatedPatient.getInsuranceProviderId());
        responseDto.setStatus(updatedPatient.getStatus());

        return responseDto;
    }

    @Override
    @Transactional
    public void deletePatient(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        patientRepo.delete(patient);
    }
}