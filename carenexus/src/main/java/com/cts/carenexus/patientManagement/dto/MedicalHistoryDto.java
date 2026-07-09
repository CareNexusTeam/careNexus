package com.cts.carenexus.patientManagement.dto;

import com.cts.carenexus.patientManagement.entities.MedicalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryDto {
    private Long historyId;
    private Long patientId;
    private String condition;
    private LocalDate diagnosedDate;
    private MedicalStatus status;
}