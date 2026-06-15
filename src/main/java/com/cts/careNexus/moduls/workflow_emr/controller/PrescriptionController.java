package com.cts.careNexus.moduls.appointment_schedule.workflow_emr.controller;

import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.entity.Prescription;
import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        Prescription saved = prescriptionService.createPrescription(prescription);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientID}")
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable Integer patientID) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientID));
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<Prescription>> getByConsultation(@PathVariable Integer consultationID) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByConsultationId(consultationID));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription newData) {
        return prescriptionService.updatePrescription(id, newData)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        if (prescriptionService.deletePrescription(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}