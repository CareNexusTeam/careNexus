package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.dto.DoctorScheduleDTO;
import com.cts.careNexus.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.appointment_schedule.repository.DoctorScheduleRepository;
import com.cts.careNexus.userIdentity.entities.User;
import com.cts.careNexus.userIdentity.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleServiceImpl
        implements DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final UserRepo userRepository;

    public DoctorScheduleServiceImpl(
            DoctorScheduleRepository scheduleRepository,
            UserRepo userRepository) {

        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DoctorScheduleDTO createSchedule(
            DoctorScheduleDTO dto) {

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id : "
                                        + dto.getDoctorId()));

        DoctorSchedule schedule = new DoctorSchedule();

        schedule.setDoctorID(doctor);
        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setSlotDurationMinutes(dto.getSlotDurationMinutes());
        schedule.setAvailableSlots(dto.getAvailableSlots());

        return convertToDTO(
                scheduleRepository.save(schedule));
    }

    @Override
    public List<DoctorScheduleDTO> getAllSchedules() {

        return scheduleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorScheduleDTO> getScheduleById(
            Long id) {

        return scheduleRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<DoctorScheduleDTO> getSchedulesByDoctorId(
            Long doctorId) {

        return scheduleRepository
                .findByDoctorID_UserId(doctorId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorScheduleDTO updateSchedule(
            Long id,
            DoctorScheduleDTO dto) {

        DoctorSchedule existing =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id : "
                                                + id));

        User doctor = userRepository
                .findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id : "
                                        + dto.getDoctorId()));

        existing.setDoctorID(doctor);
        existing.setDate(dto.getDate());
        existing.setStartTime(dto.getStartTime());
        existing.setEndTime(dto.getEndTime());
        existing.setSlotDurationMinutes(
                dto.getSlotDurationMinutes());
        existing.setAvailableSlots(
                dto.getAvailableSlots());

        return convertToDTO(
                scheduleRepository.save(existing));
    }

    @Override
    public DoctorScheduleDTO patchSchedule(
            Long id,
            Map<String, Object> updates) {

        DoctorSchedule existing =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id : "
                                                + id));

        if (updates.containsKey("doctorId")) {

            Long doctorId =
                    Long.valueOf(
                            updates.get("doctorId")
                                    .toString());

            User doctor = userRepository.findById(doctorId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Doctor not found with id : "
                                            + doctorId));

            existing.setDoctorID(doctor);
        }

        if (updates.containsKey("date")) {
            existing.setDate(
                    LocalDate.parse(
                            updates.get("date").toString()));
        }

        if (updates.containsKey("startTime")) {
            existing.setStartTime(
                    LocalTime.parse(
                            updates.get("startTime").toString()));
        }

        if (updates.containsKey("endTime")) {
            existing.setEndTime(
                    LocalTime.parse(
                            updates.get("endTime").toString()));
        }

        if (updates.containsKey("slotDurationMinutes")) {
            existing.setSlotDurationMinutes(
                    Integer.valueOf(
                            updates.get("slotDurationMinutes")
                                    .toString()));
        }

        if (updates.containsKey("availableSlots")) {
            existing.setAvailableSlots(
                    Integer.valueOf(
                            updates.get("availableSlots")
                                    .toString()));
        }

        return convertToDTO(
                scheduleRepository.save(existing));
    }

    @Override
    public void deleteSchedule(Long id) {

        DoctorSchedule existing =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id : "
                                                + id));

        scheduleRepository.delete(existing);
    }

    private DoctorScheduleDTO convertToDTO(
            DoctorSchedule schedule) {

        DoctorScheduleDTO dto =
                new DoctorScheduleDTO();

        dto.setScheduleId(schedule.getScheduleID());

        dto.setDoctorId(
                schedule.getDoctorID().getUserId());

        dto.setDate(schedule.getDate());

        dto.setStartTime(schedule.getStartTime());

        dto.setEndTime(schedule.getEndTime());

        dto.setSlotDurationMinutes(
                schedule.getSlotDurationMinutes());

        dto.setAvailableSlots(
                schedule.getAvailableSlots());

        return dto;
    }
}