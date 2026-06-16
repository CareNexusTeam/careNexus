package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import com.cts.careNexus.moduls.appointment_schedule.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment createAppointment(Appointment appointment) {
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
    public Optional<Appointment> updateAppointment(Long id, Appointment appointmentDetails) {
        return appointmentRepository.findById(id).map(existingAppointment -> {
            existingAppointment.setPatientID(appointmentDetails.getPatientID());
            existingAppointment.setDoctorID(appointmentDetails.getDoctorID());
            existingAppointment.setDepartmentID(appointmentDetails.getDepartmentID());
            existingAppointment.setScheduledDateTime(appointmentDetails.getScheduledDateTime());
            existingAppointment.setType(appointmentDetails.getType());
            existingAppointment.setStatus(appointmentDetails.getStatus());

            return appointmentRepository.save(existingAppointment);
        });
    }

    @Override
    public Optional<Appointment> patchAppointment(Long id, Map<String, Object> updates) {
        return appointmentRepository.findById(id).map(existingAppointment -> {

            if (updates.containsKey("patientID")) {
                existingAppointment.setPatientID((Integer) updates.get("patientID"));
            }
            if (updates.containsKey("doctorID")) {
                existingAppointment.setDoctorID((Integer) updates.get("doctorID"));
            }
            if (updates.containsKey("departmentID")) {
                existingAppointment.setDepartmentID((Integer) updates.get("departmentID"));
            }
            if (updates.containsKey("scheduledDateTime")) {
                String dateTimeStr = (String) updates.get("scheduledDateTime");
                existingAppointment.setScheduledDateTime(dateTimeStr != null ? LocalDateTime.parse(dateTimeStr) : null);
            }
            if (updates.containsKey("type")) {
                existingAppointment.setType((String) updates.get("type"));
            }
            if (updates.containsKey("status")) {
                existingAppointment.setStatus((String) updates.get("status"));
            }

            return appointmentRepository.save(existingAppointment);
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