package com.cts.careNexus.moduls.appointment_schedule.workflow_emr.controller;

import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.entity.Referral;
import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private ReferralService referralService;

    @PostMapping
    public ResponseEntity<Referral> createReferral(@RequestBody Referral referral) {
        Referral savedReferral = referralService.createReferral(referral);
        return new ResponseEntity<>(savedReferral, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Referral>> getAllReferrals() {
        return new ResponseEntity<>(referralService.getAllReferrals(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Referral> getReferralById(@PathVariable Long id) {
        return referralService.getReferralById(id)
                .map(referral -> new ResponseEntity<>(referral, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/consultation/{consultationID}")
    public ResponseEntity<List<Referral>> getByConsultationID(@PathVariable Integer consultationID) {
        return new ResponseEntity<>(referralService.getReferralsByConsultationId(consultationID), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Referral>> getByStatus(@PathVariable String status) {
        return new ResponseEntity<>(referralService.getReferralsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Referral>> getByPriority(@PathVariable Referral.Priority priority) {
        return new ResponseEntity<>(referralService.getReferralsByPriority(priority), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Referral> updateReferral(@PathVariable Long id, @RequestBody Referral newReferral) {
        return referralService.updateReferral(id, newReferral)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferral(@PathVariable Long id) {
        if (referralService.deleteReferral(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}