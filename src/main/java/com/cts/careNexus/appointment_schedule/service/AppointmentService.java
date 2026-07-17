package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.dto.AppointmentRequestDTO;
import com.cts.careNexus.appointment_schedule.entity.Appointment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AppointmentService {

    Appointment createAppointment(AppointmentRequestDTO dto);

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    Optional<Appointment> updateAppointment(Long id, AppointmentRequestDTO dto);

    Optional<Appointment> patchAppointment(Long id, Map<String, Object> updates);

    boolean deleteAppointment(Long id);
}
