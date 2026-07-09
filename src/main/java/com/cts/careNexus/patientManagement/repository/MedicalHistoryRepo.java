package com.cts.careNexus.patientManagement.repository;

import com.cts.careNexus.patientManagement.entities.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByPatientPatientId(Long patientId);
}
