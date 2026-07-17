package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.PrescriptionDTO;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    PrescriptionDTO createPrescription(PrescriptionDTO dto);

    List<PrescriptionDTO> getAllPrescriptions();

    Optional<PrescriptionDTO> getPrescriptionById(Long id);

    List<PrescriptionDTO> getPrescriptionsByPatientId(Long patientId);

    List<PrescriptionDTO> getPrescriptionsByConsultationId(Long consultationId);

    PrescriptionDTO updatePrescription(Long id,
                                       PrescriptionDTO dto);

    void deletePrescription(Long id);
}