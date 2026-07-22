package com.cts.careNexus.PharmacyAndMedical.dto;

import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DrugDTO {

    private Long drugId;
    private String drugName;
    private String category;
    private int quantityInStock;
    private int reorderLevel;
    private double pricePerUnit;
    private LocalDate expiryDate;
    private DrugStatus status;
}