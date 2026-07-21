package com.cts.careNexus.PharmacyAndMedical.service;

import com.cts.careNexus.PharmacyAndMedical.dto.DrugDTO;
import com.cts.careNexus.PharmacyAndMedical.entities.DrugInventory;
import com.cts.careNexus.PharmacyAndMedical.enums.DrugStatus;
import com.cts.careNexus.PharmacyAndMedical.repository.DrugRepository;
import com.cts.careNexus.exception.InvalidRequestException;
import com.cts.careNexus.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepo;
 // this method is for converting DrugInventory
    private DrugDTO convertToDTO(DrugInventory drug) {

        DrugDTO dto = new DrugDTO();

        dto.setDrugId(drug.getDrugId());
        dto.setDrugName(drug.getDrugName());
        dto.setCategory(drug.getCategory());
        dto.setQuantityInStock(drug.getQuantityInStock());
        dto.setReorderLevel(drug.getReorderLevel());
        dto.setPricePerUnit(drug.getPricePerUnit());
        dto.setExpiryDate(drug.getExpiryDate());
        dto.setStatus(drug.getStatus());

        return dto;
    }

    @Override
    public DrugDTO addDrug(DrugDTO dto) {
        // Check if medicine already exists
        boolean exists = drugRepo.existsByDrugNameIgnoreCaseAndCategoryIgnoreCase(
                dto.getDrugName(), dto.getCategory());
        if (exists) {
            throw new InvalidRequestException("Medicine already exists");
        }
        DrugInventory drug = new DrugInventory();
        drug.setDrugName(dto.getDrugName());
        drug.setCategory(dto.getCategory());
        drug.setQuantityInStock(dto.getQuantityInStock());
        drug.setReorderLevel(dto.getReorderLevel());
        drug.setPricePerUnit(dto.getPricePerUnit());
        drug.setExpiryDate(dto.getExpiryDate());

        if (dto.getStatus() != null) {
            drug.setStatus(dto.getStatus());

        }

        return convertToDTO(drugRepo.save(drug));

    }

    @Override
    public List<DrugDTO> getAllDrugs() {

        return drugRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrugDTO> filterDrugs(String category, DrugStatus status) {

        return drugRepo.findByCategoryAndStatus(category, status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DrugDTO getDrugById(Long id) {

        DrugInventory drug = drugRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Drug not found " + id));

        return convertToDTO(drug);
    }

    @Override
    public String deleteDrug(Long id) {

        if (!drugRepo.existsById(id)) {
            throw new ResourceNotFoundException("Drug not found");
        }

        drugRepo.deleteById(id);

        return "Drug deleted successfully";
    }

    @Override
    public DrugDTO updateStock(Long id, int quantity) {

        DrugInventory drug = drugRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Drug not found"));

        drug.setQuantityInStock(drug.getQuantityInStock() + quantity);

        return convertToDTO(drugRepo.save(drug));
    }

    @Override
    public DrugDTO updateDrugStatus(Long id, DrugStatus status) {

        DrugInventory drug = drugRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        drug.setStatus(status);

        return convertToDTO(drugRepo.save(drug));
    }

    @Override
    public List<DrugDTO> searchDrugs(String keyword) {

        return drugRepo.findAll().stream().filter(d ->
                        d.getDrugName().toLowerCase().contains(keyword.toLowerCase()) ||
                                d.getCategory().toLowerCase().contains(keyword.toLowerCase())).map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrugDTO> getExpiringDrugs() {

        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        return drugRepo.findAll()
                .stream()
                .filter(d -> d.getExpiryDate().isBefore(next30Days))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrugDTO> getLowStock() {

        return drugRepo.findAll().stream().filter(d ->
                        d.getQuantityInStock() <= d.getReorderLevel())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}