package com.carenexus.carenexus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class MedicalHistoryDto {

    @NotBlank(message = "Condition name is mandatory")
    private String conditionName;

    @NotNull(message = "Diagnosed date is mandatory")
    private LocalDate diagnosedDate;

    @NotBlank(message = "Status is mandatory (e.g., Active, Resolved)")
    private String status;
}