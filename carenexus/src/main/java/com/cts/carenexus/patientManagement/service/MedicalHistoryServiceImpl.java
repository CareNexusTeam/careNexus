package com.cts.carenexus.patientManagement.service;

import com.cts.carenexus.patientManagement.dto.MedicalHistoryDto;
import com.cts.carenexus.patientManagement.entities.MedicalHistory;
import com.cts.carenexus.patientManagement.entities.MedicalStatus;
import com.cts.carenexus.patientManagement.entities.Patient;
import com.cts.carenexus.patientManagement.repository.MedicalHistoryRepo;
import com.cts.carenexus.patientManagement.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepo medicalHistoryRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistoryDto> getMedicalHistoryByPatient(Long patientId) {
        if (!patientRepo.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }

        List<MedicalHistory> histories = medicalHistoryRepo.findByPatientPatientId(patientId);
        List<MedicalHistoryDto> dtoList = new ArrayList<>();

        for (MedicalHistory mh : histories) {
            MedicalHistoryDto dto = new MedicalHistoryDto();
            dto.setHistoryId(mh.getHistoryId());
            dto.setCondition(mh.getCondition());
            dto.setDiagnosedDate(mh.getDiagnosedDate());
            dto.setStatus(mh.getStatus());
            dto.setPatientId(mh.getPatient().getPatientId());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    @Transactional
    public void addMedicalHistory(Long patientId, MedicalHistoryDto historyDto) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        MedicalHistory history = new MedicalHistory();
        history.setConditionName(historyDto.getCondition());
        history.setDiagnosedDate(historyDto.getDiagnosedDate());

        if (historyDto.getStatus() == null) {
            history.setStatus(MedicalStatus.Active);
        } else {
            history.setStatus(historyDto.getStatus());
        }

        history.setPatient(patient);

        medicalHistoryRepo.save(history);
    }

    @Override
    @Transactional
    public MedicalHistoryDto updateHistoryStatus(Long historyId, MedicalStatus status) {
        MedicalHistory history = medicalHistoryRepo.findById(historyId)
                .orElseThrow(() -> new RuntimeException("Medical History record not found with id: " + historyId));

        history.setStatus(status);
        MedicalHistory updatedHistory = medicalHistoryRepo.save(history);

        MedicalHistoryDto responseDto = new MedicalHistoryDto();
        responseDto.setHistoryId(updatedHistory.getHistoryId());
        responseDto.setCondition(updatedHistory.getCondition());
        responseDto.setDiagnosedDate(updatedHistory.getDiagnosedDate());
        responseDto.setStatus(updatedHistory.getStatus());
        responseDto.setPatientId(updatedHistory.getPatient().getPatientId());

        return responseDto;
    }
}