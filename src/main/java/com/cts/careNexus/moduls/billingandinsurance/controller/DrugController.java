package com.cts.careNexus.moduls.billingandinsurance.controller;


import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;
import com.cts.careNexus.moduls.billingandinsurance.model.DrugInventory;
import com.cts.careNexus.moduls.billingandinsurance.service.DrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
public class DrugController {

    private static final Logger log = LoggerFactory.getLogger(DrugController.class);

    @Autowired
    private  DrugService drugService;

    @PostMapping
    public DrugInventory addDrug(@RequestBody DrugInventory drug) {
        log.info("API: Adding drug {}", drug.getDrugName());
        return drugService.addDrug(drug);
    }


    @GetMapping("/{drugId}")
    public DrugInventory getDrugById(@PathVariable Long drugId) {

        log.info("API: Fetch drug {}", drugId);

        return drugService.getDrugById(drugId);
    }


    @GetMapping
    public List<DrugInventory> getDrugs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) DrugStatus status) {

        log.info("API: Fetch drugs");
         System.out.println(category);
        System.out.println(status);

        if (category != null && status != null) {
            return drugService.filterDrugs(category, status);
        }
        return drugService.getAllDrugs();

    }


    @DeleteMapping("/{drugId}")
    public String deleteDrug(@PathVariable Long drugId) {

        log.info("API: Delete drug {}", drugId);

        return drugService.deleteDrug(drugId);
    }


    @PatchMapping("/{drugId}/stock")
    public DrugInventory updateStock(@PathVariable Long drugId,
                                     @RequestParam int quantity) {

        log.info("API: Update stock for {}", drugId);

        return drugService.updateStock(drugId, quantity);
    }

    @PatchMapping("/{drugId}/status")
    public DrugInventory updateStatus(@PathVariable Long drugId,
                                      @RequestParam DrugStatus status) {

        log.info("API: Update status for {}", drugId);

        return drugService.updateDrugStatus(drugId, status);
    }

    @GetMapping("/search")
    public List<DrugInventory> searchDrugs(@RequestParam String keyword) {

        log.info("API: Search drugs {}", keyword);

        return drugService.searchDrugs(keyword);
    }

    @GetMapping("/expiring")
    public List<DrugInventory> getExpiringDrugs() {

        log.info("API: Expiry alerts");

        return drugService.getExpiringDrugs();
    }


    @GetMapping("/low-stock")
    public List<DrugInventory> getLowStock() {

        log.info("API: Low stock drugs");

        return drugService.getLowStock();
    }





}