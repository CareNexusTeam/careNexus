package com.cts.careNexus.PharmacyAndMedical.entities;
import com.cts.careNexus.PharmacyAndMedical.enums.DispensationStatus;
import com.cts.careNexus.exception.InvalidRequestException;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Dispensation")
@Data
@RequiredArgsConstructor
public class Dispensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dispensation_id")
    private Long dispensationID;


    @ManyToOne
    @JoinColumn(name = "Drug_id", nullable = false)
    private DrugInventory drug;



    @ManyToOne
    @JoinColumn(name = "Prescription_id", nullable = false)
    private Prescription prescription;



    @Column(name = "Quantity_Dispensed", nullable = false)
    private int quantityDispensed;


    @ManyToOne
    @JoinColumn(name = "DispensedBy_id", nullable = false)
    private User dispensedByID;


    @Column(name = "Dispensation_Date", nullable = false)
    private LocalDateTime dispensationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 25)
    private DispensationStatus status;




    @PrePersist
    public void onCreate() {
        this.dispensationDate = LocalDateTime.now();
    }



    @PreUpdate
    public void validate() {
        if (quantityDispensed <= 0) {
            throw new InvalidRequestException("Quantity must be greater than 0");

        }

        if (drug == null) {
            throw new InvalidRequestException("Drug cannot be null");
        }

    }
}

