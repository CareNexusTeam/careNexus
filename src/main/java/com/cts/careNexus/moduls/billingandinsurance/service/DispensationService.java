package com.cts.careNexus.moduls.billingandinsurance.service;

import com.cts.careNexus.moduls.billingandinsurance.model.Dispensation;

import java.util.List;

public interface DispensationService {
    List<Dispensation> getAllDispensations();
    Dispensation getDispensationById(Long id);
    public List<Dispensation> getByPrescription(Long prescriptionId);
    public List<Dispensation> getPendingDispensations();
    public List<Dispensation> getDispensationHistory();
    public String dispenseDrug(Long prescriptionId, Long drugId, int quantity, Long userId);

}
