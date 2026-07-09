package com.cts.carenexus.patientManagement.repository;

import com.cts.carenexus.patientManagement.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

}
