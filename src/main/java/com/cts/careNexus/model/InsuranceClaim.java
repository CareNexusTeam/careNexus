package com.cts.careNexus.model;

import com.cts.careNexus.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "InsuranceClaim")
@Data
public class InsuranceClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClaimID")
    private Long claimID;


    @ManyToOne
    @JoinColumn(name = "InvoiceID", nullable = false)
    private Invoice invoice;

    @Column(name = "InsuranceProviderID", nullable = false)
    private Long insuranceProviderID;

    @Column(name = "ClaimAmount", nullable = false)
    private double claimAmount;

    @Column(name = "SubmissionDate", nullable = false)
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
    }
}
