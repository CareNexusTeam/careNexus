package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.controller;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.InvoiceStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.Invoice;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service.InvoiceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceServiceImpl invoiceService;


    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {

        log.info("API: Creating invoice for patient {}", invoice.getPatientID());

        return invoiceService.createInvoice(invoice);
    }


    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {

        log.info("API: Fetch invoice {}", id);

        return invoiceService.getInvoiceById(id);
    }

    @GetMapping
    public List<Invoice> getInvoices(
            @RequestParam(required = false) InvoiceStatus status) {

        log.info("API: Fetch invoices");

        if (status != null) {
            return invoiceService.getByStatus(status);
        }

        return invoiceService.getAllInvoices();
    }

    @PatchMapping("/{id}/payment")
    public Invoice updatePayment(@PathVariable Long id,
                                 @RequestParam double amount) {

        log.info("API: Payment update for invoice {}", id);

        return invoiceService.updatePayment(id, amount);
    }

    @PatchMapping("/{id}/cancel")
    public Invoice cancelInvoice(@PathVariable Long id) {

        log.info("API: Cancel invoice {}", id);

        return invoiceService.cancelInvoice(id);
    }

    @GetMapping("/patients/{id}/invoices")
    public List<Invoice> getInvoicesByPatient(@PathVariable Long id) {

        log.info("API: Fetch invoices for patient {}", id);

        return invoiceService.getInvoicesByPatient(id);

    }


}
