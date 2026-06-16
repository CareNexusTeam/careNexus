package com.cts.careNexus.moduls.appointment_schedule.workflow_emr.service;

import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.entity.Consultation;
import com.cts.careNexus.moduls.appointment_schedule.workflow_emr.repository.ConsultationRepository;
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
        return consultationRepository.findById(id);
    }

    @Override
    public Optional<Consultation> updateConsultation(Long id, Consultation newData) {
        return consultationRepository.findById(id).map(existing -> {
            existing.setAppointmentID(newData.getAppointmentID());
            existing.setPatientID(newData.getPatientID());
            existing.setDoctorID(newData.getDoctorID());
            existing.setSymptoms(newData.getSymptoms());
            existing.setDiagnosis(newData.getDiagnosis());
            existing.setTreatmentPlan(newData.getTreatmentPlan());
            existing.setConsultationDate(newData.getConsultationDate());
            existing.setStatus(newData.getStatus());

            return consultationRepository.save(existing);
        });
    }

    @Override
    public boolean deleteConsultation(Long id) {
        if (consultationRepository.existsById(id)) {
            consultationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}