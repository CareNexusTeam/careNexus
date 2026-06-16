package com.cts.careNexus.moduls.billingandinsurance.model;

import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "DrugInventory")
@Data
public class DrugInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Drug_id")
    private Long drugID;

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
            throw new RuntimeException("Quantity cannot be negative");
        }
    }
}
