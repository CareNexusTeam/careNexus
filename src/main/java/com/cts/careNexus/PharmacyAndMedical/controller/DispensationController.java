package com.cts.careNexus.PharmacyAndMedical.controller;

import com.cts.careNexus.PharmacyAndMedical.dto.DispensationDTO;
import com.cts.careNexus.PharmacyAndMedical.service.DispensationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DispensationController {

    @Autowired
    private DispensationServiceImpl dispService;

    @PostMapping("/dispensations")
    public String dispenseDrug(@RequestParam Long prescriptionId,
                               @RequestParam Long userId) {

        System.out.println(
                "API: Dispensing using prescription " + prescriptionId);

        return dispService.dispenseDrug(prescriptionId, userId);
    }

    @GetMapping("/dispensations")
    public List<DispensationDTO> getAllDispensations() {

        System.out.println("API: Fetch all dispensations");

        return dispService.getAllDispensations();
    }

    @GetMapping("/dispensations/{id}")
    public DispensationDTO getDispensationById(
            @PathVariable Long id) {

        System.out.println(
                "API: Fetch dispensation " + id);

        return dispService.getDispensationById(id);
    }

    @GetMapping("/dispensations/pending")
    public List<DispensationDTO> getPendingDispensations() {

        System.out.println("API: Fetch pending dispensations");

        return dispService.getPendingDispensations();
    }

    @GetMapping("/dispensations/history")
    public List<DispensationDTO> getDispensationHistory() {

        System.out.println(
                "API: Fetch dispensation history");

        return dispService.getDispensationHistory();
    }

    @GetMapping("/prescriptions/{id}/dispensation")
    public List<DispensationDTO> getByPrescription(
            @PathVariable Long id) {

        System.out.println("API: Fetch dispensation for prescription " + id);

        return dispService.getByPrescription(id);
    }
}