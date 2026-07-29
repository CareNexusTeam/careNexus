package com.cts.careNexus.PharmacyAndMedical.service;

import com.cts.careNexus.PharmacyAndMedical.dto.DrugDTO;
import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;

import java.util.List;

public interface DrugService {

    DrugDTO addDrug(DrugDTO drug);
    DrugDTO getDrugById(Long id);
    List<DrugDTO> getAllDrugs();
    List<DrugDTO> filterDrugs(String category, DrugStatus status);
    String deleteDrug(Long id);
    DrugDTO updateStock(Long id, int quantity);
    DrugDTO updateDrugStatus(Long id, DrugStatus status);
    List<DrugDTO> searchDrugs(String keyword);
    List<DrugDTO> getExpiringDrugs();
    List<DrugDTO> getLowStock();
}