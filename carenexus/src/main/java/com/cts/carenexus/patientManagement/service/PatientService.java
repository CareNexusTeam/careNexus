package com.cts.carenexus.patientManagement.service;

import com.cts.carenexus.patientManagement.dto.PatientDto;
import com.cts.carenexus.patientManagement.entities.Patient;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PatientService {
    PatientDto createPatient(PatientDto patientDto);
    List<PatientDto> getAllPatients();
    PatientDto getPatientById(Long patientId);
    PatientDto updatePatientStatus(PatientDto patientDto, Long patientId);
    void deletePatient(Long patientId);
}