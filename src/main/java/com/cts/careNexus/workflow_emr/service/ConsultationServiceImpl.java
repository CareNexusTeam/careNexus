package com.cts.careNexus.workflow_emr.service;

import com.cts.careNexus.appointment_schedule.entity.Appointment;
import com.cts.careNexus.appointment_schedule.repository.AppointmentRepository;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import com.cts.careNexus.workflow_emr.dto.ConsultationDTO;
import com.cts.careNexus.workflow_emr.entity.Consultation;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.workflow_emr.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationServiceImpl
        implements ConsultationService {

    private final ConsultationRepository consultationRepository;

    private final AppointmentRepository appointmentRepository;

    private final PatientRepo patientRepository;

    private final UserRepo userRepository;

    public ConsultationServiceImpl(
            ConsultationRepository consultationRepository,
            AppointmentRepository appointmentRepository,
            PatientRepo patientRepository,
            UserRepo userRepository) {

        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ConsultationDTO createConsultation(
            ConsultationDTO dto) {

        Appointment appointment =
                appointmentRepository.findById(
                                dto.getAppointmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found with id : "
                                                + dto.getAppointmentId()));

        Patient patient =
                patientRepository.findById(
                                dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id : "
                                                + dto.getPatientId()));

        User doctor =
                userRepository.findById(
                                dto.getDoctorId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id : "
                                                + dto.getDoctorId()));

        Consultation consultation =
                new Consultation();

        consultation.setAppointment(appointment);
        consultation.setPatient(patient);
        consultation.setDoctor(doctor);
        consultation.setSymptoms(dto.getSymptoms());
        consultation.setDiagnosis(dto.getDiagnosis());
        consultation.setTreatmentPlan(dto.getTreatmentPlan());
        consultation.setConsultationDate(
                dto.getConsultationDate());

        consultation.setStatus(
                Consultation.ConsultationStatus
                        .valueOf(dto.getStatus()));

        Consultation saved =
                consultationRepository.save(consultation);

        return convertToDTO(saved);
    }

    @Override
    public List<ConsultationDTO> getAllConsultations() {

        return consultationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ConsultationDTO> getConsultationById(
            Long id) {

        return consultationRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public ConsultationDTO updateConsultation(
            Long id,
            ConsultationDTO dto) {

        Consultation existing =
                consultationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Consultation not found with id : "
                                                + id));

        Appointment appointment =
                appointmentRepository.findById(
                                dto.getAppointmentId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Appointment not found with id : "
                                                + dto.getAppointmentId()));

        Patient patient =
                patientRepository.findById(
                                dto.getPatientId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Patient not found with id : "
                                                + dto.getPatientId()));

        User doctor =
                userRepository.findById(
                                dto.getDoctorId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Doctor not found with id : "
                                                + dto.getDoctorId()));

        existing.setAppointment(appointment);
        existing.setPatient(patient);
        existing.setDoctor(doctor);
        existing.setSymptoms(dto.getSymptoms());
        existing.setDiagnosis(dto.getDiagnosis());
        existing.setTreatmentPlan(dto.getTreatmentPlan());
        existing.setConsultationDate(
                dto.getConsultationDate());

        existing.setStatus(
                Consultation.ConsultationStatus
                        .valueOf(dto.getStatus()));

        return convertToDTO(
                consultationRepository.save(existing));
    }

    @Override
    public void deleteConsultation(Long id) {

        Consultation consultation =
                consultationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Consultation not found with id : "
                                                + id));

        consultationRepository.delete(consultation);
    }

    private ConsultationDTO convertToDTO(
            Consultation consultation) {

        ConsultationDTO dto =
                new ConsultationDTO();

        dto.setConsultationId(
                consultation.getConsultationID());

        dto.setAppointmentId(
                consultation.getAppointment()
                        .getAppointmentID());

        dto.setPatientId(
                consultation.getPatient()
                        .getPatientId());

        dto.setDoctorId(
                consultation.getDoctor()
                        .getUserId());

        dto.setSymptoms(
                consultation.getSymptoms());

        dto.setDiagnosis(
                consultation.getDiagnosis());

        dto.setTreatmentPlan(
                consultation.getTreatmentPlan());

        dto.setConsultationDate(
                consultation.getConsultationDate());

        dto.setStatus(
                consultation.getStatus().name());

        return dto;
    }
}