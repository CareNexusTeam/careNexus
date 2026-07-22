package com.cts.careNexus.PharmacyAndMedical.entities;

import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import com.cts.careNexus.exception.InvalidRequestException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DrugInventory")
@Data
@RequiredArgsConstructor
public class DrugInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Drug_id")
    private Long drugId;

    @Column(name = "Drug_Name", nullable = false, length = 150)
    private String drugName;

    @Column(name = "Category", length = 100)
    private String category;

    @Column(name = "Quantity_In_Stock", nullable = false)
    private int quantityInStock;

    @Column(name = "Reorder_Level", nullable = false)
    private int reorderLevel = 50;


    @Column(name = "Price_Per_Unit", nullable = false)
    private double pricePerUnit;


    @Column(name = "Expiry_Date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 25)
    private DrugStatus status = DrugStatus.Available;


    @PrePersist
    @PreUpdate
    public void validate() {
        if (quantityInStock < 0) {
            throw new InvalidRequestException("Quantity cannot be negative");
        }
    }
}
