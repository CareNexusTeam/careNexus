package com.cts.careNexus.controller;

import com.cts.careNexus.entity.Referral;
import com.cts.careNexus.repository.ReferralRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private ReferralRepository referralRepository;

    @PostMapping
    public ResponseEntity<Referral> createReferral(@RequestBody Referral referral) {
        Referral savedReferral = referralRepository.save(referral);
        return new ResponseEntity<>(savedReferral, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Referral>> getAllReferrals() {
        List<Referral> referrals = referralRepository.findAll();
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Referral> getReferralById(@PathVariable Long id) {
        Optional<Referral> referralData = referralRepository.findById(id);

        if (referralData.isPresent()) {
            return new ResponseEntity<>(referralData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<Referral>> getByConsultationID(@PathVariable Integer consultationID) {
        List<Referral> referrals = referralRepository.findByConsultationID(consultationID);
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Referral>> getByStatus(@PathVariable String status) {
        List<Referral> referrals = referralRepository.findByStatus(status);
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Referral>> getByPriority(@PathVariable Referral.Priority priority) {
        List<Referral> referrals = referralRepository.findByPriority(priority);
        return new ResponseEntity<>(referrals, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Referral> updateReferral(
            @PathVariable Long id,
            @RequestBody Referral newReferral) {

        Optional<Referral> referralData = referralRepository.findById(id);

        if (referralData.isPresent()) {
            Referral existingReferral = referralData.get();

            existingReferral.setConsultationID(newReferral.getConsultationID());
            existingReferral.setReferredToDepartment(newReferral.getReferredToDepartment());
            existingReferral.setReason(newReferral.getReason());
            existingReferral.setPriority(newReferral.getPriority());
            existingReferral.setStatus(newReferral.getStatus());

            Referral updatedReferral = referralRepository.save(existingReferral);
            return new ResponseEntity<>(updatedReferral, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferral(@PathVariable Long id) {
        if (referralRepository.existsById(id)) {
            referralRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
