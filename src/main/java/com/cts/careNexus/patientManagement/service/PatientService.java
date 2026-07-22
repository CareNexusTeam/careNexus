package com.cts.careNexus.patientManagement.service;

import com.cts.careNexus.patientManagement.dto.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto createPatient(PatientDto patientDto);
    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long patientId);
    PatientDto updatePatientStatus(PatientDto patientDto, Long patientId);
    void deletePatient(Long patientId);
}