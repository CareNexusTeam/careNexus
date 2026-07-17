package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.ReferralDTO;
import com.cts.careNexus.workflow_emr.entity.Referral;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.repository.ReferralRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReferralServiceImpl
        implements ReferralService {

    private final ReferralRepository referralRepository;

    public ReferralServiceImpl(
            ReferralRepository referralRepository) {

        this.referralRepository = referralRepository;
    }

    @Override
    public ReferralDTO createReferral(
            ReferralDTO dto) {

        Referral referral = new Referral();

        referral.setConsultationID(
                dto.getConsultationID());

        referral.setReferredToDepartment(
                dto.getReferredToDepartment());

        referral.setReason(
                dto.getReason());

        referral.setPriority(
                Referral.Priority.valueOf(
                        dto.getPriority()));

        referral.setStatus(
                dto.getStatus());

        Referral saved =
                referralRepository.save(referral);

        return convertToDTO(saved);
    }

    @Override
    public List<ReferralDTO> getAllReferrals() {

        return referralRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReferralDTO> getReferralById(
            Long id) {

        return referralRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<ReferralDTO>
    getReferralsByConsultationId(
            Integer consultationID) {

        return referralRepository
                .findByConsultationID(consultationID)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReferralDTO>
    getReferralsByStatus(
            String status) {

        return referralRepository
                .findByStatus(status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReferralDTO>
    getReferralsByPriority(
            String priority) {

        return referralRepository
                .findByPriority(
                        Referral.Priority.valueOf(priority))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReferralDTO updateReferral(
            Long id,
            ReferralDTO dto) {

        Referral existing =
                referralRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Referral not found with id : "
                                                + id));

        existing.setConsultationID(
                dto.getConsultationID());

        existing.setReferredToDepartment(
                dto.getReferredToDepartment());

        existing.setReason(
                dto.getReason());

        existing.setPriority(
                Referral.Priority.valueOf(
                        dto.getPriority()));

        existing.setStatus(
                dto.getStatus());

        return convertToDTO(
                referralRepository.save(existing));
    }

    @Override
    public void deleteReferral(Long id) {

        Referral referral =
                referralRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Referral not found with id : "
                                                + id));

        referralRepository.delete(referral);
    }

    private ReferralDTO convertToDTO(
            Referral referral) {

        ReferralDTO dto =
                new ReferralDTO();

        dto.setReferralId(
                referral.getReferralID());

        dto.setConsultationID(
                referral.getConsultationID());

        dto.setReferredToDepartment(
                referral.getReferredToDepartment());

        dto.setReason(
                referral.getReason());

        dto.setPriority(
                referral.getPriority().name());

        dto.setStatus(
                referral.getStatus());

        return dto;
    }
}