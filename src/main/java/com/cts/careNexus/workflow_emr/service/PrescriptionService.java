package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.workflow_emr.dto.PrescriptionRequestDTO;
import com.cts.careNexus.workflow_emr.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {

    Prescription createPrescription(PrescriptionRequestDTO dto);

    List<Prescription> getAllPrescriptions();

    Optional<Prescription> getPrescriptionById(Long id);

    List<Prescription> getPrescriptionsByPatientId(Long patientId);

    List<Prescription> getPrescriptionsByConsultationId(Long consultationId);

    Optional<Prescription> updatePrescription(Long id, PrescriptionRequestDTO dto);

    boolean deletePrescription(Long id);
}