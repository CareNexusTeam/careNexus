package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.repository;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
