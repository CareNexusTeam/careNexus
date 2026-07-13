package com.cts.careNexus.moduls.workflow_emr.service;

import com.cts.careNexus.moduls.exception.ResourceNotFoundException;
import com.cts.careNexus.moduls.workflow_emr.entity.Consultation;
import com.cts.careNexus.moduls.workflow_emr.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Override
    public Consultation createConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @Override
    public Optional<Consultation> getConsultationById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));

        return Optional.of(consultation);
    }

    @Override
    public Optional<Consultation> updateConsultation(Long id, Consultation newData) {
        Consultation existing = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id));

        existing.setAppointmentID(newData.getAppointmentID());
        existing.setPatientID(newData.getPatientID());
        existing.setDoctorID(newData.getDoctorID());
        existing.setSymptoms(newData.getSymptoms());
        existing.setDiagnosis(newData.getDiagnosis());
        existing.setTreatmentPlan(newData.getTreatmentPlan());
        existing.setConsultationDate(newData.getConsultationDate());
        existing.setStatus(newData.getStatus());

        return Optional.of(consultationRepository.save(existing));
    }

    @Override
    public boolean deleteConsultation(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Consultation", "id", id);
        }

        consultationRepository.deleteById(id);
        return true;
    }
}