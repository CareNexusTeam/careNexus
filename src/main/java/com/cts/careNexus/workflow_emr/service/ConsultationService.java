package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.ConsultationDTO;

import java.util.List;
import java.util.Optional;

public interface ConsultationService {

    ConsultationDTO createConsultation(ConsultationDTO dto);

    List<ConsultationDTO> getAllConsultations();

    Optional<ConsultationDTO> getConsultationById(Long id);

    ConsultationDTO updateConsultation(Long id,
                                       ConsultationDTO dto);

    void deleteConsultation(Long id);
}