package com.cts.careNexus.billingandinsurance.entities;

import com.cts.careNexus.billingandinsurance.enums.InvoiceStatus;
import com.cts.careNexus.exception.InvalidRequestException;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Invoice")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Invoice_id")
    private Long invoiceID;

    @ManyToOne
    @JoinColumn(name = "Patient_id", nullable = false)
    private Patient patient;


    @ManyToOne
    @JoinColumn(name = "Consultation_id")
    private Consultation consultation;


    @Column(name = "Total_Amount", nullable = false)
    private double totalAmount;

    @Column(name = "Paid_Amount", nullable = false)
    private double paidAmount = 0.0;

    @Column(name = "Outstanding_Amount", nullable = false)
    private double outstandingAmount;

    @Column(name = "Invoice_Date", nullable = false)
    private LocalDateTime invoiceDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 30)
    private InvoiceStatus status;



    @PrePersist
    public void onCreate() {


        this.invoiceDate = LocalDateTime.now();

        this.outstandingAmount = this.totalAmount - this.paidAmount;


        if (totalAmount < 0 || outstandingAmount < 0) {
            throw new InvalidRequestException("Amounts cannot be negative");
        }
    }


    @PreUpdate
    public void onUpdate() {


        this.outstandingAmount = this.totalAmount - this.paidAmount;


        if (outstandingAmount == 0) {
            this.status = InvoiceStatus.Paid;
        } else {
            this.status = InvoiceStatus.PartiallyPaid;
        }


        if (totalAmount < 0 || outstandingAmount < 0) {
            throw new InvalidRequestException("Amounts cannot be negative");
        }
    }
}
