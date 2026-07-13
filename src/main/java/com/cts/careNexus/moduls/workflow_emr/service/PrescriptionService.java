package com.cts.careNexus.moduls.workflow_emr.service;

import com.cts.careNexus.moduls.workflow_emr.entity.Prescription;
import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    Prescription createPrescription(Prescription prescription);
    List<Prescription> getAllPrescriptions();
    Optional<Prescription> getPrescriptionById(Long id);
    List<Prescription> getPrescriptionsByPatientId(Integer patientID);
    List<Prescription> getPrescriptionsByConsultationId(Integer consultationID);
    Optional<Prescription> updatePrescription(Long id, Prescription newData);
    boolean deletePrescription(Long id);
}