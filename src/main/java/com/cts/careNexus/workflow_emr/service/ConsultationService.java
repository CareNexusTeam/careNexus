package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.ConsultationRequestDTO;
import com.cts.careNexus.workflow_emr.entity.Consultation;

import java.util.List;
import java.util.Optional;

public interface ConsultationService {

    Consultation createConsultation(ConsultationRequestDTO dto);

    List<Consultation> getAllConsultations();

    Optional<Consultation> getConsultationById(Long id);

    Optional<Consultation> updateConsultation(Long id, ConsultationRequestDTO dto);

    boolean deleteConsultation(Long id);
}