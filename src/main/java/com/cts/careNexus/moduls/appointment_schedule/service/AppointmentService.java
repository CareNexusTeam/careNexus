package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();
    Optional<Appointment> getAppointmentById(Long id);
    Optional<Appointment> updateAppointment(Long id, Appointment appointmentDetails);
    boolean deleteAppointment(Long id);
}