package com.cts.careNexus.model;

import com.cts.careNexus.enums.DispensationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Dispensation")
@Data
public class Dispensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DispensationID")
    private Long dispensationID;


    @ManyToOne
    @JoinColumn(name = "DrugID", nullable = false)
    private DrugInventory drug;

    @Column(name = "PrescriptionID", nullable = false)
    private Long prescriptionID;

    @Column(name = "QuantityDispensed", nullable = false)
    private int quantityDispensed;

    @Column(name = "DispensedByID", nullable = false)
    private Long dispensedByID;

    @Column(name = "DispensationDate", nullable = false)
    private LocalDateTime dispensationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 25)
    private DispensationStatus status;

    @PrePersist

    @PreUpdate
    public void validate() {
        if (quantityDispensed <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
    }
}