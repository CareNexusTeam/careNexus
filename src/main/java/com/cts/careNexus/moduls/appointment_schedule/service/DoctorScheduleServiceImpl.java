package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.dto.DoctorScheduleDto;
import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.moduls.appointment_schedule.repository.DoctorScheduleRepository;
import com.cts.careNexus.moduls.exception.BadRequestException;
import com.cts.careNexus.moduls.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Override
    public DoctorScheduleDto createSchedule(DoctorScheduleDto scheduleDTO) {

        DoctorSchedule schedule = convertToEntity(scheduleDTO);

        DoctorSchedule savedSchedule = scheduleRepository.save(schedule);

        return convertToDTO(savedSchedule);
    }

    @Override
    public List<DoctorScheduleDto> getAllSchedules() {

        return scheduleRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorScheduleDto> getScheduleById(Long id) {

        DoctorSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("DoctorSchedule", "id", id));

        return Optional.of(convertToDTO(schedule));
    }

    @Override
    public List<DoctorScheduleDto> getSchedulesByDoctorId(Integer doctorID) {

        return scheduleRepository.findByDoctorID(doctorID)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DoctorScheduleDto> updateSchedule(Long id,
                                                      DoctorScheduleDto newData) {

        DoctorSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("DoctorSchedule", "id", id));

        existing.setDoctorID(newData.getDoctorID());
        existing.setDate(newData.getDate());
        existing.setStartTime(newData.getStartTime());
        existing.setEndTime(newData.getEndTime());
        existing.setSlotDurationMinutes(newData.getSlotDurationMinutes());
        existing.setAvailableSlots(newData.getAvailableSlots());

        DoctorSchedule updatedSchedule = scheduleRepository.save(existing);

        return Optional.of(convertToDTO(updatedSchedule));
    }

    @Override
    public Optional<DoctorScheduleDto> patchSchedule(Long id,
                                                     Map<String, Object> updates) {

        DoctorSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("DoctorSchedule", "id", id));

        if (updates.containsKey("doctorID")) {
            existing.setDoctorID(
                    toInteger(updates.get("doctorID"), "doctorID"));
        }

        if (updates.containsKey("date")) {
            existing.setDate(
                    toLocalDate(updates.get("date"), "date"));
        }

        if (updates.containsKey("startTime")) {
            existing.setStartTime(
                    toLocalTime(updates.get("startTime"), "startTime"));
        }

        if (updates.containsKey("endTime")) {
            existing.setEndTime(
                    toLocalTime(updates.get("endTime"), "endTime"));
        }

        if (updates.containsKey("slotDurationMinutes")) {
            existing.setSlotDurationMinutes(
                    toInteger(
                            updates.get("slotDurationMinutes"),
                            "slotDurationMinutes"
                    )
            );
        }

        if (updates.containsKey("availableSlots")) {
            existing.setAvailableSlots(
                    toInteger(
                            updates.get("availableSlots"),
                            "availableSlots"
                    )
            );
        }

        DoctorSchedule updatedSchedule = scheduleRepository.save(existing);

        return Optional.of(convertToDTO(updatedSchedule));
    }

    @Override
    public boolean deleteSchedule(Long id) {

        if (!scheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("DoctorSchedule", "id", id);
        }

        scheduleRepository.deleteById(id);

        return true;
    }

    private DoctorScheduleDto convertToDTO(DoctorSchedule schedule) {

        DoctorScheduleDto dto = new DoctorScheduleDto();

        dto.setScheduleID(schedule.getScheduleID());
        dto.setDoctorID(schedule.getDoctorID());
        dto.setDate(schedule.getDate());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        dto.setSlotDurationMinutes(schedule.getSlotDurationMinutes());
        dto.setAvailableSlots(schedule.getAvailableSlots());

        return dto;
    }

    private DoctorSchedule convertToEntity(DoctorScheduleDto dto) {

        DoctorSchedule schedule = new DoctorSchedule();

        schedule.setScheduleID(dto.getScheduleID());
        schedule.setDoctorID(dto.getDoctorID());
        schedule.setDate(dto.getDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setSlotDurationMinutes(dto.getSlotDurationMinutes());
        schedule.setAvailableSlots(dto.getAvailableSlots());

        return schedule;
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

    private LocalDate toLocalDate(Object value, String fieldName) {

        if (value == null) {
            return null;
        }

        try {
            return LocalDate.parse(value.toString());
        } catch (Exception ex) {
            throw new BadRequestException(
                    fieldName +
                            " must be in ISO format, example: 2026-06-24"
            );
        }
    }

    private LocalTime toLocalTime(Object value, String fieldName) {

        if (value == null) {
            return null;
        }

        try {
            return LocalTime.parse(value.toString());
        } catch (Exception ex) {
            throw new BadRequestException(
                    fieldName +
                            " must be in ISO format, example: 10:30:00"
            );
        }
    }
}