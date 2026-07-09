package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.InvoiceStatus;
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

    @Column(name = "Patient_id", nullable = false)
    private Long patientID;


//    @ManyToOne
//    @JoinColumn(name = "Patient_id", nullable = false)
//    private Patient patient;


    @Column(name = "Consultation_id")
    private Long consultationID;



//    @ManyToOne
//    @JoinColumn(name = "Consultation_id")
//    private Consultation consultation;


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

        if (outstandingAmount == 0) {
            this.status = InvoiceStatus.Paid;
        } else if (paidAmount > 0) {
            this.status = InvoiceStatus.PartiallyPaid;
        } else {
            this.status = InvoiceStatus.Pending;
        }
    }



    @PreUpdate
    public void onUpdate() {

        this.outstandingAmount = this.totalAmount - this.paidAmount;

        if (outstandingAmount == 0) {
            this.status = InvoiceStatus.Paid;
        } else {
            this.status = InvoiceStatus.PartiallyPaid;
        }}

     @PrePersist
    @PreUpdate
    public void validate() {
        if (totalAmount < 0 || outstandingAmount < 0) {
            throw new RuntimeException("Amounts cannot be negative");
        }
    }
}
