package com.cts.careNexus.PharmacyAndMedical.service;

import com.cts.careNexus.PharmacyAndMedical.dto.DispensationDTO;

import java.util.List;

public interface DispensationService {
    List<DispensationDTO> getAllDispensations();
    DispensationDTO getDispensationById(Long id);
    public List<DispensationDTO> getByPrescription(Long prescriptionId);
    public List<DispensationDTO> getPendingDispensations();
    public List<DispensationDTO> getDispensationHistory();
    public String dispenseDrug(Long prescriptionId, Long userId);

}
