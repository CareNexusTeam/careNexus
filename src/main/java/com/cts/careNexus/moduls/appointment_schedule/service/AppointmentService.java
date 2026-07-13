package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.dto.AppointmentDto;
import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AppointmentService {
    AppointmentDto createAppointment(AppointmentDto appointmentDto);
    List<AppointmentDto> getAllAppointments();
    Optional<AppointmentDto> getAppointmentById(Long id);
    Optional<AppointmentDto> updateAppointment(Long id, AppointmentDto appointmentDetails);
    Optional<AppointmentDto> patchAppointment(Long id, Map<String, Object> updates); // Added for PATCH
    boolean deleteAppointment(Long id);
}