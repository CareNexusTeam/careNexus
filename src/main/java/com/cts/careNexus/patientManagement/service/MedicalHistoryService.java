package com.cts.careNexus.patientManagement.service;

import com.cts.careNexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.careNexus.patientManagement.entities.MedicalStatus;
import java.util.List;

public interface MedicalHistoryService {
    List<MedicalHistoryDto> getMedicalHistoryByPatient(Long patientId);
    void addMedicalHistory(Long patientId, MedicalHistoryDto historyDto);
    MedicalHistoryDto updateHistoryStatus(Long historyId, MedicalStatus status);
}