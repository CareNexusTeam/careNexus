package com.cts.careNexus.PharmacyAndMedical.repository;

import com.cts.careNexus.PharmacyAndMedical.entities.Dispensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispensationRepository extends JpaRepository<Dispensation,Long> {


    public  List<Dispensation>findByPrescription_PrescriptionID(Long prescriptionID);





}
