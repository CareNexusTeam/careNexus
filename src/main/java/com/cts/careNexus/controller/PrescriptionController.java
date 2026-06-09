package com.cts.careNexus.controller;
import com.cts.careNexus.entity.Prescription;
import com.cts.careNexus.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        Prescription saved = prescriptionRepository.save(prescription);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        return prescriptionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientID}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable Integer patientID) {
        return ResponseEntity.ok(prescriptionRepository.findByPatientID(patientID));
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<Prescription>> getByConsultation(@PathVariable Integer consultationID) {
        return ResponseEntity.ok(prescriptionRepository.findByConsultationID(consultationID));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @RequestBody Prescription newData) {

        return prescriptionRepository.findById(id)
                .map(existing -> {

                    existing.setConsultationID(newData.getConsultationID());
                    existing.setPatientID(newData.getPatientID());
                    existing.setMedicationName(newData.getMedicationName());
                    existing.setDosage(newData.getDosage());
                    existing.setFrequency(newData.getFrequency());
                    existing.setDuration(newData.getDuration());
                    existing.setStatus(newData.getStatus());

                    return new ResponseEntity<>(prescriptionRepository.save(existing), HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

