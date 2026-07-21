package com.cts.careNexus.billingandinsurance.service;

import com.cts.careNexus.billingandinsurance.dto.InsuranceClaimDTO;
import com.cts.careNexus.billingandinsurance.entities.InsuranceClaim;
import com.cts.careNexus.billingandinsurance.entities.Invoice;
import com.cts.careNexus.billingandinsurance.enums.ClaimStatus;
import com.cts.careNexus.billingandinsurance.repository.InsuranceClaimRepository;
import com.cts.careNexus.billingandinsurance.repository.InvoiceRepository;
import com.cts.careNexus.exception.ClaimExceedAmountException;
import com.cts.careNexus.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InsuranceClaimServiceImpl
        implements InsuranceClaimService {

    private final InsuranceClaimRepository claimRepo;

    private final InvoiceRepository invoiceRepo;

    private InsuranceClaimDTO convertToDTO(
            InsuranceClaim claim) {

        InsuranceClaimDTO dto = new InsuranceClaimDTO();

        dto.setClaimID(claim.getClaimID());
        dto.setInvoiceId(claim.getInvoice().getInvoiceID());
        dto.setInsuranceProviderId(claim.getInsuranceProviderId());
        dto.setClaimAmount(claim.getClaimAmount());
        dto.setSubmissionDate(claim.getSubmissionDate());
        dto.setStatus(claim.getStatus());

        return dto;
    }

    @Override
    public InsuranceClaimDTO createClaim(InsuranceClaimDTO claimDTO) {

        System.out.println("Service: Creating claim");
        Invoice invoice = invoiceRepo.findById(claimDTO.getInvoiceId())
                        .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        InsuranceClaim claim = new InsuranceClaim();
        claim.setInvoice(invoice);
        Integer providerId = invoice.getPatient().getInsuranceProviderId();
        claim.setInsuranceProviderId(providerId);
        claim.setClaimAmount(claimDTO.getClaimAmount());

        if (claim.getClaimAmount() > invoice.getOutstandingAmount()) {

            throw new ClaimExceedAmountException("Claim exceeds outstanding amount");
        }
        claim.setStatus(ClaimStatus.Submitted);
        return convertToDTO(claimRepo.save(claim));
    }

    @Override
    public List<InsuranceClaimDTO> getAllClaims() {
        return claimRepo.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public InsuranceClaimDTO getClaimById(Long id) {

        InsuranceClaim claim = claimRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        return convertToDTO(claim);
    }

    @Override
    public InsuranceClaimDTO updateStatus(Long id, ClaimStatus status) {

        InsuranceClaim claim = claimRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

        System.out.println("Service: Updating claim status");
        claim.setStatus(status);
        return convertToDTO(claimRepo.save(claim));
    }

    @Override
    public InsuranceClaimDTO submitClaim(
            Long id) {

        InsuranceClaim claim = claimRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Claim not found"));

        System.out.println("Service: Submitting claim");
        claim.setStatus(ClaimStatus.Submitted);

        return convertToDTO(claimRepo.save(claim));
    }
}