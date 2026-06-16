package com.cts.careNexus.moduls.billingandinsurance.controller;

import com.cts.careNexus.moduls.billingandinsurance.model.Dispensation;
import com.cts.careNexus.moduls.billingandinsurance.service.DispensationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DispensationController {
    private static final Logger log = LoggerFactory.getLogger(DispensationController.class);

    @Autowired
    private DispensationServiceImpl dispService;


    @PostMapping("/dispensations")
    public String dispenseDrug(@RequestParam Long prescriptionId,
                               @RequestParam Long drugId,
                               @RequestParam int quantity,
                               @RequestParam Long userId) {

        log.info("API: Dispensing drugId {}", drugId);

        return dispService.dispenseDrug(prescriptionId, drugId, quantity, userId);
    }

    @GetMapping("/dispensations")
    public List<Dispensation> getAllDispensations() {

        log.info("API: Fetch all dispensations");

        return dispService.getAllDispensations();
    }


    @GetMapping("/dispensations/{id}")
    public Dispensation getDispensationById(@PathVariable Long id) {

        log.info("API: Fetch dispensation {}", id);

        return dispService.getDispensationById(id);
    }


    @GetMapping("/dispensations/pending")
    public List<Dispensation> getPendingDispensations() {

        log.info("API: Fetch pending dispensations");

        return dispService.getPendingDispensations();
    }

    @GetMapping("/dispensations/history")
    public List<Dispensation> getDispensationHistory() {

        log.info("API: Fetch dispensation history");

        return dispService.getDispensationHistory();
    }

    @GetMapping("/prescriptions/{id}/dispensation")
    public List<Dispensation> getByPrescription(@PathVariable Long id) {

        log.info("API: Fetch dispensation for prescription {}", id);

        return dispService.getByPrescription(id);
    }

}
