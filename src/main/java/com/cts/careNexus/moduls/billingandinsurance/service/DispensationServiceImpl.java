package com.cts.careNexus.moduls.billingandinsurance.service;


import com.cts.careNexus.moduls.billingandinsurance.enums.DispensationStatus;
import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;
import com.cts.careNexus.moduls.billingandinsurance.model.Dispensation;
import com.cts.careNexus.moduls.billingandinsurance.model.DrugInventory;
import com.cts.careNexus.moduls.billingandinsurance.repository.DispensationRepository;
import com.cts.careNexus.moduls.billingandinsurance.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DispensationServiceImpl implements DispensationService{

    private static final Logger log = LoggerFactory.getLogger(DispensationServiceImpl.class);



//    @Autowired
//    private PrescriptionRepository prescriptionRepo;


//    @Autowired
//    private UserRepository userRepo;


    @Autowired
    private DrugRepository drugRepo;

    @Autowired
    private DispensationRepository dispRepo;

    @Override
    public List<Dispensation> getAllDispensations() {
        return dispRepo.findAll();
    }


    @Override
    public Dispensation getDispensationById(Long id) {

        return dispRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispensation not found"));
    }

    @Override
    public List<Dispensation> getByPrescription(Long prescriptionID) {

        return dispRepo.findAllByPrescriptionID(prescriptionID);


//        return dispRepo.findAllByPrescription_PrescriptionID(prescriptionId);

    }


    public List<Dispensation> getPendingDispensations() {

        return dispRepo.findAll()
                .stream()
                .filter(d -> d.getStatus() == DispensationStatus.Pending)
                .toList();
    }


    public List<Dispensation> getDispensationHistory() {

        return dispRepo.findAll()
                .stream()
                .filter(d -> d.getStatus() == DispensationStatus.Dispensed
                        || d.getStatus() == DispensationStatus.Partial)
                .toList();
    }


//backlog
//
//   Prescription Module Integration
//   Fetch prescription using prescriptionId
//   Validate drug belongs to prescription
//   Calculate quantity using:
//   quantity =  frequency × duration


    public String dispenseDrug(Long prescriptionId, Long drugId,int quantity, Long userId) {

        log.info("Service: Dispensing drugId {}", drugId);

        //  Fetch drug
        DrugInventory drug = drugRepo.findById(drugId)
                .orElseThrow(() -> new RuntimeException("Drug not found"));

        // Check expiry
        if (drug.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Drug expired");
        }

        // Check stock
        int availableStock = drug.getQuantityInStock();

        DispensationStatus status;

        if (availableStock >= quantity) {
            status = DispensationStatus.Dispensed;
        } else if (availableStock > 0) {
            status = DispensationStatus.Partial;
            quantity = availableStock;
        } else {
            status = DispensationStatus.Pending;
            quantity = 0;
        }

        // Step 4: Reduce stock
        drug.setQuantityInStock(drug.getQuantityInStock() - quantity);

        if (drug.getQuantityInStock() <= 0) {
            drug.setStatus(DrugStatus.OutOfStock);
        }

        drugRepo.save(drug);

        //  Save dispensation
        Dispensation d = new Dispensation();
        d.setPrescriptionID(prescriptionId);
        d.setDrug(drug);
        d.setQuantityDispensed(quantity);
        d.setDispensedByID(userId);
        d.setDispensationDate(LocalDateTime.now());
        d.setStatus(status);

        dispRepo.save(d);

        // Alert
        if (drug.getQuantityInStock() <= drug.getReorderLevel()) {
            log.warn("Low stock for {}", drug.getDrugName());
        }

        return "Status: " + status + ", Quantity Given: " + quantity;
    }


//    @Override
//    public String dispenseDrug(Long prescriptionId, Long drugId,
//                               int quantity, Long userId) {
//
//        log.info("Service: Dispensing using prescription {}", prescriptionId);
//
//
//        Prescription prescription = prescriptionRepo.findById(prescriptionId)
//                .orElseThrow(() -> new RuntimeException("Prescription not found"));
//
//
//        String medicineName = prescription.getMedicationName();
//
//        DrugInventory drug = drugRepo
//                .findByDrugNameIgnoreCase(medicineName)
//                .orElseThrow(() -> new RuntimeException("Drug not found"));
//
//
//        User user = userRepo.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//
//        if (drug.getExpiryDate().isBefore(LocalDate.now())) {
//            throw new RuntimeException("Drug expired");
//        }
//
//
//
//        int frequency = prescription.getFrequency();
//        int duration = prescription.getDuration();
//
//        quantity = frequency * duration;
//
//
//        int availableStock = drug.getQuantityInStock();
//
//        DispensationStatus status;
//
//        if (availableStock >= quantity) {
//            status = DispensationStatus.Dispensed;
//        } else if (availableStock > 0) {
//            status = DispensationStatus.Partial;
//            quantity = availableStock;
//        } else {
//            status = DispensationStatus.Pending;
//            quantity = 0;
//        }
//
//
//        drug.setQuantityInStock(availableStock - quantity);
//
//        if (drug.getQuantityInStock() <= 0) {
//            drug.setStatus(DrugStatus.OutOfStock);
//        }
//
//        drugRepo.save(drug);
//
//
//        Dispensation d = new Dispensation();
//        d.setPrescription(prescription);
//        d.setDrug(drug);
//        d.setQuantityDispensed(quantity);
//        d.setDispensedBy(user);
//        d.setDispensationDate(LocalDateTime.now());
//        d.setStatus(status);
//
//        dispRepo.save(d);
//
//
//        if (drug.getQuantityInStock() <= drug.getReorderLevel()) {
//            log.warn("Low stock for {}", drug.getDrugName());
//        }
//
//        return "Status: " + status + ", Quantity Given: " + quantity;
//    }


}
