package com.cts.careNexus.moduls.billingandinsurance.service;

import com.cts.careNexus.moduls.billingandinsurance.model.DrugInventory;
import com.cts.careNexus.moduls.billingandinsurance.repository.DrugRepository;
import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugServiceImpl implements DrugService{
    private static final Logger log = LoggerFactory.getLogger(DrugServiceImpl.class);

    @Autowired
    private DrugRepository drugRepo;


    public DrugInventory addDrug(DrugInventory drug)
    {
        log.info("Service: Saving drug {}", drug.getDrugName());

        return drugRepo.save(drug);
    }

    public List<DrugInventory> getAllDrugs() {
        return drugRepo.findAll();
    }



    public List<DrugInventory> filterDrugs(String category, DrugStatus status) {
        return drugRepo.findByCategoryAndStatus(category,status);
    }


    @Override
    public DrugInventory getDrugById(Long id) {
        return drugRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));
    }

    @Override
    public String deleteDrug(Long id) {

        if (!drugRepo.existsById(id)) {
            throw new RuntimeException("Drug not found");
        }

        drugRepo.deleteById(id);

        return "Drug deleted successfully";
    }

    @Override
    public DrugInventory updateStock(Long id, int quantity) {

        DrugInventory drug = drugRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));

        drug.setQuantityInStock(drug.getQuantityInStock() + quantity);

        return drugRepo.save(drug);
    }

    @Override
    public DrugInventory updateDrugStatus(Long id, DrugStatus status) {

        DrugInventory drug = drugRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Drug not found"));

        drug.setStatus(status);

        return drugRepo.save(drug);
    }

    @Override
    public List<DrugInventory> searchDrugs(String keyword) {

        return drugRepo.findAll().stream()
                .filter(d -> d.getDrugName().toLowerCase().contains(keyword.toLowerCase())
                        || d.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }


    public List<DrugInventory> getExpiringDrugs() {

        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        return drugRepo.findAll().stream()
                .filter(d -> d.getExpiryDate().isBefore(next30Days))
                .collect(Collectors.toList());
    }


    public List<DrugInventory> getLowStock() {

        return drugRepo.findAll().stream()
                .filter(d -> d.getQuantityInStock() <= d.getReorderLevel())
                .collect(Collectors.toList());
    }






}
