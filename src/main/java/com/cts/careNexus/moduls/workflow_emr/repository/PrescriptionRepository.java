package com.cts.careNexus.moduls.workflow_emr.repository;

import com.cts.careNexus.moduls.workflow_emr.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Optional useful queries
    List<Prescription> findByPatientID(Integer patientID);

    List<Prescription> findByConsultationID(Integer consultationID);
}