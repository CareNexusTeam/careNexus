package com.cts.careNexus.PharmacyAndMedical.service;

import com.cts.careNexus.PharmacyAndMedical.dto.DispensationDTO;
import com.cts.careNexus.PharmacyAndMedical.entities.Dispensation;
import com.cts.careNexus.PharmacyAndMedical.entities.DrugInventory;
import com.cts.careNexus.PharmacyAndMedical.enums.DispensationStatus;
import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import com.cts.careNexus.PharmacyAndMedical.repository.DispensationRepository;
import com.cts.careNexus.PharmacyAndMedical.repository.DrugRepository;
import com.cts.careNexus.exception.DrugExpiredException;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.workflow_emr.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DispensationServiceImpl implements DispensationService {

    private final PrescriptionRepository prescriptionRepo;
    private final UserRepo userRepo;
    private final DrugRepository drugRepo;
    private final DispensationRepository dispRepo;
    //this method is to convert entity Dispensation to Dto
    private DispensationDTO convertToDTO(Dispensation d) {

        DispensationDTO dto = new DispensationDTO();
        dto.setDispensationID(d.getDispensationID());
        dto.setDrugId(d.getDrug().getDrugId());
        dto.setDrugName(d.getDrug().getDrugName());
        dto.setPrescriptionId(d.getPrescription().getPrescriptionID());
        dto.setQuantityDispensed(d.getQuantityDispensed());
        dto.setDispensedById(d.getDispensedByID().getUserId());
        dto.setDispensationDate(d.getDispensationDate());
        dto.setStatus(d.getStatus());

        return dto;
    }

    @Override
    public List<DispensationDTO> getAllDispensations() {

        return dispRepo.findAll().stream().map(this::convertToDTO).toList();
    }

    @Override
    public DispensationDTO getDispensationById(Long id) {

        Dispensation dispensation = dispRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispensation not found"));
        return convertToDTO(dispensation);
    }

    @Override
    public List<DispensationDTO> getByPrescription(Long prescriptionID) {

        return dispRepo.findByPrescription_PrescriptionID(prescriptionID)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<DispensationDTO> getPendingDispensations() {

        return dispRepo.findAll().stream().filter(d -> d.getStatus() == DispensationStatus.Pending)
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<DispensationDTO> getDispensationHistory() {

        return dispRepo.findAll().stream().filter(d ->
                        d.getStatus() == DispensationStatus.Dispensed ||
                                d.getStatus() == DispensationStatus.Partial).map(this::convertToDTO).toList();
    }


    //this method takes prescriptionId,DispensedById to dispense medicine
    @Override
    public String dispenseDrug(Long prescriptionId, Long userId) {

        System.out.println("Service: Dispensing using prescription " + prescriptionId);

        Prescription prescription = prescriptionRepo.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found"));

        String medicineName = prescription.getMedicationName();

        DrugInventory drug = drugRepo.findByDrugNameIgnoreCase(medicineName)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (drug.getExpiryDate().isBefore(LocalDate.now())) {
            throw new DrugExpiredException("Drug expired");
        }

        int frequency = prescription.getFrequency();
        int duration = prescription.getDuration();
        int quantityDispensed = frequency * duration;
        int availableStock = drug.getQuantityInStock();
        DispensationStatus status;
        if (availableStock >= quantityDispensed) {
            status = DispensationStatus.Dispensed;
        }
        else if (availableStock > 0) {
            status = DispensationStatus.Partial;
            quantityDispensed = availableStock;
        }
        else {
            status = DispensationStatus.Pending;
            quantityDispensed = 0;
        }

        drug.setQuantityInStock(availableStock - quantityDispensed);

        if (drug.getQuantityInStock() <= 0) {
            drug.setStatus(DrugStatus.OutOfStock);
        }

        drugRepo.save(drug);

        Dispensation d = new Dispensation();
        d.setPrescription(prescription);
        d.setDrug(drug);
        d.setQuantityDispensed(quantityDispensed);
        d.setDispensedByID(user);
        d.setDispensationDate(LocalDateTime.now());
        d.setStatus(status);

        dispRepo.save(d);

        if (drug.getQuantityInStock() <= drug.getReorderLevel()) {
            System.out.println("Low stock for " + drug.getDrugName());
        }

        return "Status: " + status + ", Quantity Given: " + quantityDispensed;
    }

    public void deleteDispensation(Long id) {
        if (!dispRepo.existsById(id)) {
            throw new ResourceNotFoundException("Dispensation not found");
        }

        dispRepo.deleteById(id);
    }
}