package com.cts.careNexus.controller;

import com.cts.careNexus.entity.Consultation;
import com.cts.careNexus.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationRepository consultationRepository;

    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody Consultation consultation) {
        Consultation saved = consultationRepository.save(consultation);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        return new ResponseEntity<>(consultationRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Optional<Consultation> data = consultationRepository.findById(id);

        return data.map(consultation -> new ResponseEntity<>(consultation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable Long id,
                                                           @RequestBody Consultation newData) {

        Optional<Consultation> existing = consultationRepository.findById(id);

        if (existing.isPresent()) {
            Consultation consultation = existing.get();

            consultation.setAppointmentID(newData.getAppointmentID());
            consultation.setPatientID(newData.getPatientID());
            consultation.setDoctorID(newData.getDoctorID());
            consultation.setSymptoms(newData.getSymptoms());
            consultation.setDiagnosis(newData.getDiagnosis());
            consultation.setTreatmentPlan(newData.getTreatmentPlan());
            consultation.setConsultationDate(newData.getConsultationDate());
            consultation.setStatus(newData.getStatus());

            return new ResponseEntity<>(consultationRepository.save(consultation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteConsultation(@PathVariable Long id) {
        if (consultationRepository.existsById(id)) {
            consultationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}