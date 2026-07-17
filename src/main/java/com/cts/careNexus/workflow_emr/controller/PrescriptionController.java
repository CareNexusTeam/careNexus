package com.cts.careNexus.workflow_emr.controller;

import com.cts.careNexus.workflow_emr.dto.PrescriptionRequestDTO;
import com.cts.careNexus.workflow_emr.entity.Prescription;
import com.cts.careNexus.workflow_emr.service.PrescriptionService;
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
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionRequestDTO dto) {
        return new ResponseEntity<>(prescriptionService.createPrescription(dto), HttpStatus.CREATED);
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
    public ResponseEntity<List<Prescription>> getByPatient(@PathVariable Long patientID) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientID));
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<Prescription>> getByConsultation(@PathVariable Long consultationID) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByConsultationId(consultationID));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @RequestBody PrescriptionRequestDTO dto) {

        return prescriptionService.updatePrescription(id, dto)
                .map(ResponseEntity::ok)
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