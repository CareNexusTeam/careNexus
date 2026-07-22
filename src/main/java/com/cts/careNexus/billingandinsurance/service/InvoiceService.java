package com.cts.careNexus.billingandinsurance.service;

import com.cts.careNexus.billingandinsurance.dto.InvoiceDTO;
import com.cts.careNexus.billingandinsurance.enums.InvoiceStatus;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
    InvoiceDTO getInvoiceById(Long id);
    List<InvoiceDTO> getAllInvoices();
    List<InvoiceDTO> getByStatus(InvoiceStatus status);
    InvoiceDTO updatePayment(Long id, double amount);
    InvoiceDTO cancelInvoice(Long id);
    List<InvoiceDTO> getInvoicesByPatient(Long patientId);
}