package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import com.cts.careNexus.appointment_schedule.entity.Appointment;
import com.cts.careNexus.appointment_schedule.repository.AppointmentRepository;
import com.cts.careNexus.workflow_emr.dto.ConsultationRequestDTO;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Autowired
    private UserRepo userRepository;

    @Override
    public Consultation createConsultation(ConsultationRequestDTO dto) {

        Consultation consultation = new Consultation();

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        consultation.setAppointment(appointment);
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms(dto.getSymptoms());
        consultation.setDiagnosis(dto.getDiagnosis());
        consultation.setTreatmentPlan(dto.getTreatmentPlan());
        consultation.setConsultationDate(dto.getConsultationDate());
        consultation.setStatus(Consultation.ConsultationStatus.valueOf(dto.getStatus()));

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
    public Optional<Consultation> updateConsultation(Long id, ConsultationRequestDTO dto) {

        return consultationRepository.findById(id).map(existing -> {

            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElseThrow();
            Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();
            User doctor = userRepository.findById(dto.getDoctorId()).orElseThrow();

            existing.setAppointment(appointment);
            existing.setPatient(patient);
            existing.setDoctor(doctor);
            existing.setSymptoms(dto.getSymptoms());
            existing.setDiagnosis(dto.getDiagnosis());
            existing.setTreatmentPlan(dto.getTreatmentPlan());
            existing.setConsultationDate(dto.getConsultationDate());
            existing.setStatus(Consultation.ConsultationStatus.valueOf(dto.getStatus()));

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