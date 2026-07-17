package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.patientManagement.entities.Patient;
import com.cts.careNexus.patientManagement.repository.PatientRepo;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import com.cts.careNexus.appointment_schedule.dto.AppointmentRequestDTO;
import com.cts.careNexus.appointment_schedule.entity.Appointment;
import com.cts.careNexus.appointment_schedule.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PatientRepo patientRepository;

    @Override
    public Appointment createAppointment(AppointmentRequestDTO dto) {

        Appointment appointment = new Appointment();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User department = userRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        appointment.setPatientID(patient);
        appointment.setDoctorID(doctor);
        appointment.setDepartmentId(department);
        appointment.setScheduledDateTime(dto.getScheduledDateTime());
        appointment.setType(dto.getType());
        appointment.setStatus(dto.getStatus());

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Optional<Appointment> updateAppointment(Long id, AppointmentRequestDTO dto) {

        return appointmentRepository.findById(id).map(existing -> {

            Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();
            User doctor = userRepository.findById(dto.getDoctorId()).orElseThrow();
            User department = userRepository.findById(dto.getDepartmentId()).orElseThrow();

            existing.setPatientID(patient);
            existing.setDoctorID(doctor);
            existing.setDepartmentId(department);
            existing.setScheduledDateTime(dto.getScheduledDateTime());
            existing.setType(dto.getType());
            existing.setStatus(dto.getStatus());

            return appointmentRepository.save(existing);
        });
    }

    @Override
    public Optional<Appointment> patchAppointment(Long id, Map<String, Object> updates) {
        return appointmentRepository.findById(id).map(existing -> {

            if (updates.containsKey("status")) {
                existing.setStatus((String) updates.get("status"));
            }
            if (updates.containsKey("type")) {
                existing.setType((String) updates.get("type"));
            }

            return appointmentRepository.save(existing);
        });
    }

    @Override
    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}