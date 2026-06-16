package com.cts.careNexus.moduls.billingandinsurance.repository;

import com.cts.careNexus.moduls.billingandinsurance.model.Dispensation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispensationRepository extends JpaRepository<Dispensation,Long> {
    public List<Dispensation> findAllByPrescriptionID(Long prescriptionID);


}
