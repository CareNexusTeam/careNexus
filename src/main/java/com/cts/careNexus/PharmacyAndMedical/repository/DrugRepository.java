package com.cts.careNexus.PharmacyAndMedical.repository;

import com.cts.careNexus.PharmacyAndMedical.entities.DrugInventory;
import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugRepository
        extends JpaRepository<DrugInventory, Long> {

    List<DrugInventory> findByCategoryAndStatus(String category, DrugStatus status);

    Optional<DrugInventory> findByDrugNameIgnoreCase(String drugName);

    boolean existsByDrugNameIgnoreCaseAndCategoryIgnoreCase(String drugName, String category);
}