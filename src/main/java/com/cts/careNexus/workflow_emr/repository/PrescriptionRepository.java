package com.cts.careNexus.workflow_emr.repository;

import com.cts.careNexus.workflow_emr.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByConsultation_ConsultationID(Long consultationId);

    List<Prescription> findByPatient_PatientId(Long patientId);
}