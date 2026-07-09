package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.InvoiceStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.Invoice;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InvoiceServiceImpl implements InvoiceService{

    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Autowired
    private InvoiceRepository invoiceRepo;

    //    @Autowired
//    private PrescriptionRepository prescriptionRepo;


//    @Autowired
//    private DispensationRepository dispRepo;



    public Invoice createInvoice(Invoice invoice)
    {

        log.info("Service: Creating invoice");


        double outstanding = invoice.getTotalAmount() - invoice.getPaidAmount();
        invoice.setOutstandingAmount(outstanding);
        if (outstanding == 0) {
            invoice.setStatus(InvoiceStatus.Paid);
        } else {
            invoice.setStatus(InvoiceStatus.Pending);
        }

        return invoiceRepo.save(invoice);




/*
        @Override
        public Invoice createInvoice(Invoice invoice) {

        log.info("Service: Creating invoice for patient {}",
                invoice.getPatientID());

        double total = 0.0;


        List<Prescription> prescriptions =
                prescriptionRepo.findByPatientID(invoice.getPatientID());


        for (Prescription p : prescriptions) {


            List<Dispensation> disps =
                    dispRepo.findByPrescriptionID(p.getPrescriptionID());


            for (Dispensation d : disps) {

                int quantity = d.getQuantityDispensed();
                double price = d.getDrug().getPricePerUnit();

                total += quantity * price;
            }
        }


        invoice.setTotalAmount(total);


        double outstanding =
                total - invoice.getPaidAmount();

        invoice.setOutstandingAmount(outstanding);


        if (outstanding == 0) {
            invoice.setStatus(InvoiceStatus.Paid);
        } else if (invoice.getPaidAmount() > 0) {
            invoice.setStatus(InvoiceStatus.PartiallyPaid);
        } else {
            invoice.setStatus(InvoiceStatus.Pending);
        }

        return invoiceRepo.save(invoice);
    }*/

    }





    public Invoice getInvoiceById(Long id) {

        return invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepo.findAll();
    }


    public List<Invoice> getByStatus(InvoiceStatus status) {

        return invoiceRepo.findAll()
                .stream()
                .filter(i -> i.getStatus() == status)
                .toList();
    }

    public Invoice updatePayment(Long id, double amount) {

        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        log.info("Service: Updating payment for invoice {}", id);

        double newPaid = invoice.getPaidAmount() + amount;
        invoice.setPaidAmount(newPaid);

        double outstanding = invoice.getTotalAmount() - newPaid;
        invoice.setOutstandingAmount(outstanding);

        if (outstanding == 0) {
            invoice.setStatus(InvoiceStatus.Paid);
        } else {
            invoice.setStatus(InvoiceStatus.PartiallyPaid);
        }

        return invoiceRepo.save(invoice);
    }


    public Invoice cancelInvoice(Long id) {

        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        log.info("Service: Cancelling invoice {}", id);

        invoice.setStatus(InvoiceStatus.Cancelled);

        return invoiceRepo.save(invoice);
    }

    public List<Invoice> getInvoicesByPatient(Long patientId) {

        return invoiceRepo.findAll()
                .stream()
                .filter(i -> i.getPatientID().equals(patientId))
                .toList();
    }
}
