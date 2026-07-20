package com.cts.careNexus.patientManagement.service;

import com.cts.careNexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.careNexus.patientManagement.entities.MedicalHistory;
import com.cts.careNexus.patientManagement.entities.MedicalStatus;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.MedicalHistoryRepo;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    @Autowired
    private PatientRepo patientRepo;

    // Validates the patient ID and fetches their associated medical history records mapped to DTOs.
    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistoryDto> getMedicalHistoryByPatient(Long patientId) {
        if (!patientRepo.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }

        return medicalHistoryRepo.findByPatientPatientId(patientId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Finds the patient, converts the DTO to an entity, links the patient, and saves the new record.
    @Override
    @Transactional
    public void addMedicalHistory(Long patientId, MedicalHistoryDto historyDto) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        MedicalHistory history = mapToEntity(historyDto);
        history.setPatient(patient);

        medicalHistoryRepo.save(history);
    }

    // Fetches the specific medical history record, updates its status, and returns the updated DTO.
    @Override
    @Transactional
    public MedicalHistoryDto updateHistoryStatus(Long historyId, MedicalStatus status) {
        MedicalHistory history = medicalHistoryRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("Medical History record not found with id: " + historyId));

        history.setStatus(status);
        MedicalHistory updatedHistory = medicalHistoryRepo.save(history);
        return mapToDto(updatedHistory);
    }

    // Transforms a MedicalHistory entity object into a MedicalHistoryDto data transfer object.
    private MedicalHistoryDto mapToDto(MedicalHistory mh) {
        MedicalHistoryDto dto = new MedicalHistoryDto();
        dto.setHistoryId(mh.getHistoryId());
        dto.setCondition(mh.getCondition());
        dto.setDiagnosedDate(mh.getDiagnosedDate());
        dto.setStatus(mh.getStatus());

        if (mh.getPatient() != null) {
            dto.setPatientId(mh.getPatient().getPatientId());
        }
        return dto;
    }

    // Transforms a MedicalHistoryDto into a MedicalHistory database entity, defaulting null status to Active.
    private MedicalHistory mapToEntity(MedicalHistoryDto dto) {
        MedicalHistory history = new MedicalHistory();
        history.setHistoryId(dto.getHistoryId());
        history.setCondition(dto.getCondition());
        history.setDiagnosedDate(dto.getDiagnosedDate());

        if (dto.getStatus() == null) {
            history.setStatus(MedicalStatus.Active);
        } else {
            history.setStatus(dto.getStatus());
        }

        return history;
    }
}