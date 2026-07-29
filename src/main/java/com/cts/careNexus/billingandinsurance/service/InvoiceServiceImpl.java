package com.cts.careNexus.billingandinsurance.service;

import com.cts.careNexus.PharmacyAndMedical.entities.Dispensation;
import com.cts.careNexus.PharmacyAndMedical.repository.DispensationRepository;
import com.cts.careNexus.billingandinsurance.dto.InvoiceDTO;
import com.cts.careNexus.billingandinsurance.entities.Invoice;
import com.cts.careNexus.billingandinsurance.enums.InvoiceStatus;
import com.cts.careNexus.billingandinsurance.repository.InvoiceRepository;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import com.cts.careNexus.workflow_emr.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepo;

    private final PrescriptionRepository prescriptionRepo;

    private final DispensationRepository dispRepo;

    private final ConsultationRepository consultationRepo;

    private InvoiceDTO convertToDTO(Invoice invoice) {

        InvoiceDTO dto = new InvoiceDTO();

        dto.setInvoiceID(invoice.getInvoiceID());

        dto.setPatientId(invoice.getPatient().getPatientId());

        if (invoice.getConsultation() != null) {
            dto.setConsultationId(
                    invoice.getConsultation()
                            .getConsultationID());
        }

        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaidAmount(invoice.getPaidAmount());
        dto.setOutstandingAmount(invoice.getOutstandingAmount());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setStatus(invoice.getStatus());
        return dto;
    }

    @Override
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) {

        System.out.println("Service: Creating invoice for patient "
                        + invoiceDTO.getPatientId());

        Invoice invoice = new Invoice();
        Patient patient = new Patient();
        patient.setPatientId(invoiceDTO.getPatientId());
        invoice.setPatient(patient);
        invoice.setPaidAmount(invoiceDTO.getPaidAmount());
        if (invoiceDTO.getConsultationId() != null) {

            Consultation consultation =
                    consultationRepo.findById(invoiceDTO.getConsultationId()).orElseThrow(()
                            -> new ResourceNotFoundException("Consultation not found"));

            invoice.setConsultation(consultation);
        }

        double total = 0.0;

        List<Prescription> prescriptions = prescriptionRepo.findByPatient_PatientId(
                        invoiceDTO.getPatientId());

        for (Prescription p : prescriptions) {

            List<Dispensation> disps = dispRepo.findByPrescription_PrescriptionID(
                            p.getPrescriptionID());

            for (Dispensation d : disps) {

                int quantity =
                        d.getQuantityDispensed();

                double price =
                        d.getDrug().getPricePerUnit();

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

        return convertToDTO(invoiceRepo.save(invoice));
    }

    @Override
    public InvoiceDTO getInvoiceById(Long id) {

        Invoice invoice = invoiceRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Invoice not found"));

        return convertToDTO(invoice);
    }

    @Override
    public List<InvoiceDTO> getAllInvoices() {

        return invoiceRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<InvoiceDTO> getByStatus(
            InvoiceStatus status) {

        return invoiceRepo.findAll()
                .stream()
                .filter(i -> i.getStatus() == status)
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public InvoiceDTO updatePayment(Long id, double amount) {

        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invoice not found"));

        System.out.println("Service: Updating payment for invoice " + id);

        double newPaid = invoice.getPaidAmount() + amount;

        invoice.setPaidAmount(newPaid);

        double outstanding = invoice.getTotalAmount() - newPaid;

        invoice.setOutstandingAmount(outstanding);

        if (outstanding == 0) {

            invoice.setStatus(InvoiceStatus.Paid);

        } else {

            invoice.setStatus(InvoiceStatus.PartiallyPaid);
        }
        return convertToDTO(invoiceRepo.save(invoice));
    }

    @Override
    public InvoiceDTO cancelInvoice(Long id) {

        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        System.out.println(
                "Service: Cancelling invoice " + id);

        invoice.setStatus(InvoiceStatus.Cancelled);

        return convertToDTO(invoiceRepo.save(invoice));
    }

    @Override
    public List<InvoiceDTO> getInvoicesByPatient(
            Long patientId) {

        return invoiceRepo
                .findByPatient_PatientId(patientId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
}