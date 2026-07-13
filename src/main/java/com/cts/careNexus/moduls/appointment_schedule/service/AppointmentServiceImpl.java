package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.dto.AppointmentDto;
import com.cts.careNexus.moduls.appointment_schedule.entity.Appointment;
import com.cts.careNexus.moduls.appointment_schedule.repository.AppointmentRepository;
import com.cts.careNexus.moduls.exception.BadRequestException;
import com.cts.careNexus.moduls.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDTO) {

        Appointment appointment = convertToEntity(appointmentDTO);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return convertToDTO(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppointmentDto> getAppointmentById(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment", "id", id));

        return Optional.of(convertToDTO(appointment));
    }

    @Override
    public Optional<AppointmentDto> updateAppointment(
            Long id,
            AppointmentDto appointmentDetails) {

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment", "id", id));

        existingAppointment.setPatientID(appointmentDetails.getPatientID());
        existingAppointment.setDoctorID(appointmentDetails.getDoctorID());
        existingAppointment.setDepartmentID(appointmentDetails.getDepartmentID());
        existingAppointment.setScheduledDateTime(
                appointmentDetails.getScheduledDateTime());
        existingAppointment.setType(appointmentDetails.getType());
        existingAppointment.setStatus(appointmentDetails.getStatus());

        Appointment updatedAppointment =
                appointmentRepository.save(existingAppointment);

        return Optional.of(convertToDTO(updatedAppointment));
    }

    @Override
    public Optional<AppointmentDto> patchAppointment(
            Long id,
            Map<String, Object> updates) {

        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment", "id", id));

        if (updates.containsKey("patientID")) {
            existingAppointment.setPatientID(
                    toInteger(updates.get("patientID"), "patientID"));
        }

        if (updates.containsKey("doctorID")) {
            existingAppointment.setDoctorID(
                    toInteger(updates.get("doctorID"), "doctorID"));
        }

        if (updates.containsKey("departmentID")) {
            existingAppointment.setDepartmentID(
                    toInteger(updates.get("departmentID"), "departmentID"));
        }

        if (updates.containsKey("scheduledDateTime")) {
            existingAppointment.setScheduledDateTime(
                    toLocalDateTime(
                            updates.get("scheduledDateTime"),
                            "scheduledDateTime"
                    )
            );
        }

        if (updates.containsKey("type")) {
            existingAppointment.setType(
                    toStringValue(updates.get("type"), "type"));
        }

        if (updates.containsKey("status")) {
            existingAppointment.setStatus(
                    toStringValue(updates.get("status"), "status"));
        }

        Appointment updatedAppointment =
                appointmentRepository.save(existingAppointment);

        return Optional.of(convertToDTO(updatedAppointment));
    }

    @Override
    public boolean deleteAppointment(Long id) {

        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment", "id", id);
        }

        appointmentRepository.deleteById(id);

        return true;
    }

    private AppointmentDto convertToDTO(Appointment appointment) {

        AppointmentDto dto = new AppointmentDto();

        dto.setAppointmentID(appointment.getAppointmentID());
        dto.setPatientID(appointment.getPatientID());
        dto.setDoctorID(appointment.getDoctorID());
        dto.setDepartmentID(appointment.getDepartmentID());
        dto.setScheduledDateTime(appointment.getScheduledDateTime());
        dto.setType(appointment.getType());
        dto.setStatus(appointment.getStatus());

        return dto;
    }

    private Appointment convertToEntity(AppointmentDto dto) {

        Appointment appointment = new Appointment();

        appointment.setAppointmentID(dto.getAppointmentID());
        appointment.setPatientID(dto.getPatientID());
        appointment.setDoctorID(dto.getDoctorID());
        appointment.setDepartmentID(dto.getDepartmentID());
        appointment.setScheduledDateTime(dto.getScheduledDateTime());
        appointment.setType(dto.getType());
        appointment.setStatus(dto.getStatus());

        return appointment;
    }

    private Integer toInteger(Object value, String fieldName) {

        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex) {
            throw new BadRequestException(fieldName + " must be a valid number");
        }
    }

    private String toStringValue(Object value, String fieldName) {

        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        }

        throw new BadRequestException(fieldName + " must be a valid string");
    }

    private LocalDateTime toLocalDateTime(Object value, String fieldName) {

        if (value == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(value.toString());
        } catch (Exception ex) {
            throw new BadRequestException(
                    fieldName
                            + " must be in ISO format, example: 2026-06-24T10:30:00"
            );
        }
    }
}