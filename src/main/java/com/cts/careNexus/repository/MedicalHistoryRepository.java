package com.carenexus.carenexus.repository;

import com.carenexus.carenexus.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

    List<MedicalHistory> findByPatientPatientId(int patientId);
}