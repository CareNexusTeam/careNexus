package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.entity.Referral;

import java.util.List;
import java.util.Optional;

public interface ReferralService {
    Referral createReferral(Referral referral);
    List<Referral> getAllReferrals();
    Optional<Referral> getReferralById(Long id);
    List<Referral> getReferralsByConsultationId(Integer consultationID);
    List<Referral> getReferralsByStatus(String status);
    List<Referral> getReferralsByPriority(Referral.Priority priority);
    Optional<Referral> updateReferral(Long id, Referral newReferral);
    boolean deleteReferral(Long id);
}