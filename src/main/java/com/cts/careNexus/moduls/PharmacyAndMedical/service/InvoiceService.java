package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.InvoiceStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.Invoice;

import java.util.List;

public interface InvoiceService {
    public Invoice createInvoice(Invoice invoice);

    public Invoice getInvoiceById(Long id);
    public List<Invoice> getAllInvoices();
    public List<Invoice> getByStatus(InvoiceStatus status);
    public Invoice updatePayment(Long id, double amount);
    public Invoice cancelInvoice(Long id);
    public List<Invoice> getInvoicesByPatient(Long patientId);
}
