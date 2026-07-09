package com.cts.careNexus.moduls.billingandinsurance.service;
import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;
import com.cts.careNexus.moduls.billingandinsurance.model.DrugInventory;

import java.util.List;


public interface DrugService {
    public DrugInventory addDrug(DrugInventory drug);
    public DrugInventory getDrugById(Long id);
    public List<DrugInventory> getAllDrugs();
    public List<DrugInventory> filterDrugs(String category, DrugStatus status);
    public String deleteDrug(Long id);
    public DrugInventory updateStock(Long id, int quantity);
    public DrugInventory updateDrugStatus(Long id, DrugStatus status);
    public List<DrugInventory> searchDrugs(String keyword);
    public List<DrugInventory> getExpiringDrugs();
    public List<DrugInventory> getLowStock();
}
