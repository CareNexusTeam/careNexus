package com.cts.carenexus.patientManagement.service;

import com.cts.carenexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.carenexus.patientManagement.entities.MedicalStatus;
import java.util.List;

public interface MedicalHistoryService {
    List<MedicalHistoryDto> getMedicalHistoryByPatient(Long patientId);
    void addMedicalHistory(Long patientId, MedicalHistoryDto historyDto);
    MedicalHistoryDto updateHistoryStatus(Long historyId, MedicalStatus status);
}