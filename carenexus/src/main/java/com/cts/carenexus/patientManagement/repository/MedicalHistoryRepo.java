package com.cts.carenexus.patientManagement.repository;

import com.cts.carenexus.patientManagement.entities.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPatientPatientId(Long patientId);
}
