package com.cts.careNexus.moduls.workflow_emr.service;

import com.cts.careNexus.moduls.workflow_emr.entity.Consultation;
import java.util.List;
import java.util.Optional;

public interface ConsultationService {
    Consultation createConsultation(Consultation consultation);
    List<Consultation> getAllConsultations();
    Optional<Consultation> getConsultationById(Long id);
    Optional<Consultation> updateConsultation(Long id, Consultation newData);
    boolean deleteConsultation(Long id);
}