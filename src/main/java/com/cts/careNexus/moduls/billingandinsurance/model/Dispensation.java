package com.cts.careNexus.moduls.billingandinsurance.model;
import com.cts.careNexus.moduls.billingandinsurance.enums.DispensationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Dispensation")
@Data
public class Dispensation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Dispensation_id")
    private Long dispensationID;


    @ManyToOne
    @JoinColumn(name = "Drug_id", nullable = false)
    private DrugInventory drug;

    @Column(name = "Prescription_id", nullable = false)
    private Long prescriptionID;


//    @ManyToOne
//    @JoinColumn(name = "Prescription_id", nullable = false)
//    private Prescription prescription;



    @Column(name = "Quantity_Dispensed", nullable = false)
    private int quantityDispensed;

    @Column(name = "DispensedBy_id", nullable = false)
    private Long dispensedByID;



//    @ManyToOne
//    @JoinColumn(name = "DispensedBy_id", nullable = false)
//    private User dispensedBy;



    @Column(name = "Dispensation_Date", nullable = false)
    private LocalDateTime dispensationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 25)
    private DispensationStatus status;




    @PrePersist
    public void onCreate() {
        this.dispensationDate = LocalDateTime.now();
    }

    @PrePersist

    @PreUpdate
    public void validate() {
        if (quantityDispensed <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");

        }

        if (drug == null) {
            throw new RuntimeException("Drug cannot be null");
        }

    }
}

