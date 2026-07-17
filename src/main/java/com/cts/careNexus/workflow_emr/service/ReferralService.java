package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.ReferralDTO;

import java.util.List;
import java.util.Optional;

public interface ReferralService {

    ReferralDTO createReferral(ReferralDTO dto);

    List<ReferralDTO> getAllReferrals();

    Optional<ReferralDTO> getReferralById(Long id);

    List<ReferralDTO> getReferralsByConsultationId(
            Integer consultationID);

    List<ReferralDTO> getReferralsByStatus(
            String status);

    List<ReferralDTO> getReferralsByPriority(
            String priority);

    ReferralDTO updateReferral(
            Long id,
            ReferralDTO dto);

    void deleteReferral(Long id);
}