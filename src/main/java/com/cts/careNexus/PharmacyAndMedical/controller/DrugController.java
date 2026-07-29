package com.cts.careNexus.PharmacyAndMedical.controller;

import com.cts.careNexus.PharmacyAndMedical.dto.DrugDTO;
import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import com.cts.careNexus.PharmacyAndMedical.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins ="https://localhost:4200")

@RestController
@RequestMapping("/api/drugs")
public class DrugController {

    @Autowired
    private DrugService drugService;

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @PostMapping
    public DrugDTO addDrug(@RequestBody DrugDTO drug) {

        System.out.println("API: Adding drug " + drug.getDrugName());

        return drugService.addDrug(drug);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @GetMapping("/{drugId}")
    public DrugDTO getDrugById(@PathVariable Long drugId) {

        System.out.println("API: Fetch drug " + drugId);

        return drugService.getDrugById(drugId);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @GetMapping
    public List<DrugDTO> getDrugs(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) DrugStatus status) {

        System.out.println("API: Fetch drugs");
        System.out.println("Category: " + category);
        System.out.println("Status: " + status);

        if (category != null && status != null) {
            return drugService.filterDrugs(category, status);
        }

        return drugService.getAllDrugs();
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @DeleteMapping("/{drugId}")
    public String deleteDrug(@PathVariable Long drugId) {

        System.out.println("API: Delete drug " + drugId);

        return drugService.deleteDrug(drugId);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @PatchMapping("/{drugId}/stock")
    public DrugDTO updateStock(@PathVariable Long drugId,
                               @RequestParam int quantity) {

        System.out.println("API: Update stock for " + drugId);

        return drugService.updateStock(drugId, quantity);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @PatchMapping("/{drugId}/status")
    public DrugDTO updateStatus(@PathVariable Long drugId,
                                @RequestParam DrugStatus status) {

        System.out.println("API: Update status for " + drugId);

        return drugService.updateDrugStatus(drugId, status);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @GetMapping("/search")
    public List<DrugDTO> searchDrugs(@RequestParam String keyword) {

        System.out.println("API: Search drugs " + keyword);

        return drugService.searchDrugs(keyword);
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @GetMapping("/expiring")
    public List<DrugDTO> getExpiringDrugs() {

        System.out.println("API: Expiry alerts");

        return drugService.getExpiringDrugs();
    }

    @PreAuthorize(("hasRole('Pharmacist') or hasRole('Admin)"))
    @GetMapping("/low-stock")
    public List<DrugDTO> getLowStock() {

        System.out.println("API: Low stock drugs");

        return drugService.getLowStock();
    }
}