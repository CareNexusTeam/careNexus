package com.cts.careNexus.moduls.workflow_emr.service;

import com.cts.careNexus.moduls.exception.ResourceNotFoundException;
import com.cts.careNexus.moduls.workflow_emr.entity.Prescription;
import com.cts.careNexus.moduls.workflow_emr.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id));

        return Optional.of(prescription);
    }

    @Override
    public List<Prescription> getPrescriptionsByPatientId(Integer patientID) {
        return prescriptionRepository.findByPatientID(patientID);
    }

    @Override
    public List<Prescription> getPrescriptionsByConsultationId(Integer consultationID) {
        return prescriptionRepository.findByConsultationID(consultationID);
    }

    @Override
    public Optional<Prescription> updatePrescription(Long id, Prescription newData) {
        Prescription existing = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id));

        existing.setConsultationID(newData.getConsultationID());
        existing.setPatientID(newData.getPatientID());
        existing.setMedicationName(newData.getMedicationName());
        existing.setDosage(newData.getDosage());
        existing.setFrequency(newData.getFrequency());
        existing.setDuration(newData.getDuration());
        existing.setStatus(newData.getStatus());

        return Optional.of(prescriptionRepository.save(existing));
    }

    @Override
    public boolean deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription", "id", id);
        }

        prescriptionRepository.deleteById(id);
        return true;
    }
}