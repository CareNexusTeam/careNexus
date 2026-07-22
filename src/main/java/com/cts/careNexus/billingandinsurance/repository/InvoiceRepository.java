package com.cts.careNexus.billingandinsurance.repository;

import com.cts.careNexus.billingandinsurance.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByPatient_PatientId(Long patientID);
}
