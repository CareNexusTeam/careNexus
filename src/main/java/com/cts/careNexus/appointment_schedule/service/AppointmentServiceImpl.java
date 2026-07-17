package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.dto.AppointmentDTO;
import com.cts.careNexus.appointment_schedule.entity.Appointment;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.appointment_schedule.repository.AppointmentRepository;
import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepo userRepository;
    private final PatientRepo patientRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            UserRepo userRepository,
            PatientRepo patientRepository) {

        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id : "
                                        + dto.getPatientId()));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id : "
                                        + dto.getDoctorId()));

        User department = userRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id : "
                                        + dto.getDepartmentId()));

        Appointment appointment = new Appointment();

        appointment.setPatientID(patient);
        appointment.setDoctorID(doctor);
        appointment.setDepartmentId(department);
        appointment.setScheduledDateTime(dto.getScheduledDateTime());
        appointment.setType(dto.getType());
        appointment.setStatus(dto.getStatus());

        return convertToDTO(
                appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppointmentDTO> getAppointmentById(Long id) {

        return appointmentRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public AppointmentDTO updateAppointment(
            Long id,
            AppointmentDTO dto) {

        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id : " + id));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id : "
                                        + dto.getPatientId()));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id : "
                                        + dto.getDoctorId()));

        User department = userRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with id : "
                                        + dto.getDepartmentId()));

        existing.setPatientID(patient);
        existing.setDoctorID(doctor);
        existing.setDepartmentId(department);
        existing.setScheduledDateTime(dto.getScheduledDateTime());
        existing.setType(dto.getType());
        existing.setStatus(dto.getStatus());

        return convertToDTO(
                appointmentRepository.save(existing));
    }

    @Override
    public void deleteAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id : " + id));

        appointmentRepository.delete(appointment);
    }

    private AppointmentDTO convertToDTO(Appointment appointment) {

        AppointmentDTO dto = new AppointmentDTO();

        dto.setAppointmentId(
                appointment.getAppointmentID());

        dto.setDepartmentId(
                appointment.getDepartmentId().getUserId());

        dto.setDoctorId(
                appointment.getDoctorID().getUserId());

        dto.setPatientId(
                appointment.getPatientID().getPatientId());

        dto.setScheduledDateTime(
                appointment.getScheduledDateTime());

        dto.setType(
                appointment.getType());

        dto.setStatus(
                appointment.getStatus());

        return dto;
    }
}