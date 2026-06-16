package com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.service;

import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.enums.ClaimStatus;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.model.InsuranceClaim;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.repository.InvoiceRepository;
import com.cts.careNexus.moduls.billingandinsurance.PharmacyAndMedical.repository.InsuranceClaimRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceClaimServiceImpl implements InsuranceClaimService {
    private static final Logger log = LoggerFactory.getLogger(InsuranceClaimServiceImpl.class);

    @Autowired
    private InsuranceClaimRepository claimRepo;



    @Autowired
    private InvoiceRepository invoiceRepo;



    public InsuranceClaim createClaim(InsuranceClaim claim) {

        log.info("Service: Creating claim");

        claim.setStatus(ClaimStatus.Submitted);

        return claimRepo.save(claim);



//
//        public InsuranceClaim createClaim(InsuranceClaim claim) {
//
//            log.info("Service: Creating claim");
//
//
//            Invoice invoice = invoiceRepo.findById(
//                    claim.getInvoice().getInvoiceID()).orElseThrow(() ->
//                    new RuntimeException("Invoice not found"));
//
//
//            claim.setInvoice(invoice);
//
//
//            Long providerId = invoice.getPatient().getInsuranceProviderID();
//
//
//            claim.setInsuranceProviderID(providerId);
//
//
//            if (claim.getClaimAmount() > invoice.getOutstandingAmount()) {
//                throw new RuntimeException("Claim exceeds outstanding amount");
//            }

//        claim.setStatus(ClaimStatus.Submitted);
//
//        return claimRepo.save(claim);
//    }


}

    public List<InsuranceClaim> getAllClaims() {
        return claimRepo.findAll();
    }

    public InsuranceClaim getClaimById(Long id) {

        return claimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
    }

    public InsuranceClaim updateStatus(Long id, ClaimStatus status) {

        InsuranceClaim claim = claimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        log.info("Service: Updating claim status");

        claim.setStatus(status);

        return claimRepo.save(claim);
    }

    public InsuranceClaim submitClaim(Long id) {

        InsuranceClaim claim = claimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        log.info("Service: Submitting claim");

        claim.setStatus(ClaimStatus.Submitted);

        return claimRepo.save(claim);
    }







}
