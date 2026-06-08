package com.carenexus.carenexus.repository;

import com.carenexus.carenexus.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findByNameContainingOrPhone(String name, String phone);
}