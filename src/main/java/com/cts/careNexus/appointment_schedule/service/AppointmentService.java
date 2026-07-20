package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.dto.AppointmentDTO;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO dto);

    List<AppointmentDTO> getAllAppointments();

    Optional<AppointmentDTO> getAppointmentById(Long id);

    AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);

    void deleteAppointment(Long id);
}