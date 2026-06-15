package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import com.cts.careNexus.moduls.appointment_schedule.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}