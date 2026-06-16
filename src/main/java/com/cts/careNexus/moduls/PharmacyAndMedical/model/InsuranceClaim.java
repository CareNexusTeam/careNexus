package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;



@Entity
@Table(name = "InsuranceClaim")
@Data
public class InsuranceClaim {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Claim_id")
    private Long claimID;


    @ManyToOne
    @JoinColumn(name = "Invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "InsuranceProvider_id", nullable = false)
    private Long insuranceProviderID;

    @Column(name = "Claim_Amount", nullable = false)
    private double claimAmount;

    @Column(name = "Submission_Date", nullable = false)
    private LocalDateTime submissionDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 30)
    private ClaimStatus status;


    @PrePersist
    @PreUpdate
    public void validate() {
        if (claimAmount < 0) {
            throw new RuntimeException("Claim amount cannot be negative");
        }


        if (invoice == null) {
            throw new RuntimeException("Invoice cannot be null");
        }

        if (insuranceProviderID == null) {
            throw new RuntimeException("Insurance Provider ID cannot be null");
        }

    }
}
