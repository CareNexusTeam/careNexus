package com.cts.careNexus.PharmacyAndMedical.dto;

import com.cts.careNexus.PharmacyAndMedical.enums.DispensationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DispensationDTO {

    private Long dispensationID;

    private Long drugId;
    private String drugName;
    private Long prescriptionId;
    private int quantityDispensed;
    private Long dispensedById;
    private LocalDateTime dispensationDate;
    private DispensationStatus status;
}