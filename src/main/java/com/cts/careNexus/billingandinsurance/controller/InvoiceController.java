package com.cts.careNexus.billingandinsurance.controller;

import com.cts.careNexus.billingandinsurance.dto.InvoiceDTO;
import com.cts.careNexus.billingandinsurance.enums.InvoiceStatus;
import com.cts.careNexus.billingandinsurance.service.InvoiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceServiceImpl invoiceService;

    @PostMapping
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {

        System.out.println(
                "API: Creating invoice for patient "
                        + invoiceDTO.getPatientId());

        return invoiceService.createInvoice(invoiceDTO);
    }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable Long id) {

        System.out.println("API: Fetch invoice " + id);

        return invoiceService.getInvoiceById(id);
    }

    @GetMapping
    public List<InvoiceDTO> getInvoices(
            @RequestParam(required = false)
            InvoiceStatus status) {

        System.out.println("API: Fetch invoices");

        if (status != null) {
            return invoiceService.getByStatus(status);
        }

        return invoiceService.getAllInvoices();
    }

    @PatchMapping("/{id}/payment")
    public InvoiceDTO updatePayment(
            @PathVariable Long id,
            @RequestParam double amount) {

        System.out.println(
                "API: Payment update for invoice " + id);

        return invoiceService.updatePayment(id, amount);
    }

    @PatchMapping("/{id}/cancel")
    public InvoiceDTO cancelInvoice(@PathVariable Long id) {

        System.out.println("API: Cancel invoice " + id);

        return invoiceService.cancelInvoice(id);
    }

    @GetMapping("/patients/{id}/invoices")
    public List<InvoiceDTO> getInvoicesByPatient(
            @PathVariable Long id) {

        System.out.println(
                "API: Fetch invoices for patient " + id);

        return invoiceService.getInvoicesByPatient(id);
    }
}
