package com.cts.careNexus.moduls.workflow_emr.service;

import com.cts.careNexus.moduls.exception.ResourceNotFoundException;
import com.cts.careNexus.moduls.workflow_emr.entity.Referral;
import com.cts.careNexus.moduls.workflow_emr.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private ReferralRepository referralRepository;

    @Override
    public Referral createReferral(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public List<Referral> getAllReferrals() {
        return referralRepository.findAll();
    }

    @Override
    public Optional<Referral> getReferralById(Long id) {
        Referral referral = referralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referral", "id", id));

        return Optional.of(referral);
    }

    @Override
    public List<Referral> getReferralsByConsultationId(Integer consultationID) {
        return referralRepository.findByConsultationID(consultationID);
    }

    @Override
    public List<Referral> getReferralsByStatus(String status) {
        return referralRepository.findByStatus(status);
    }

    @Override
    public List<Referral> getReferralsByPriority(Referral.Priority priority) {
        return referralRepository.findByPriority(priority);
    }

    @Override
    public Optional<Referral> updateReferral(Long id, Referral newReferral) {
        Referral existingReferral = referralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Referral", "id", id));

        existingReferral.setConsultationID(newReferral.getConsultationID());
        existingReferral.setReferredToDepartment(newReferral.getReferredToDepartment());
        existingReferral.setReason(newReferral.getReason());
        existingReferral.setPriority(newReferral.getPriority());
        existingReferral.setStatus(newReferral.getStatus());

        return Optional.of(referralRepository.save(existingReferral));
    }

    @Override
    public boolean deleteReferral(Long id) {
        if (!referralRepository.existsById(id)) {
            throw new ResourceNotFoundException("Referral", "id", id);
        }

        referralRepository.deleteById(id);
        return true;
    }
}