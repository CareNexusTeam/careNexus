package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.dto.DoctorScheduleDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorScheduleService {
    DoctorScheduleDto createSchedule(DoctorScheduleDto schedule);
    List<DoctorScheduleDto> getAllSchedules();
    Optional<DoctorScheduleDto> getScheduleById(Long id);
    List<DoctorScheduleDto> getSchedulesByDoctorId(Integer doctorID);
    Optional<DoctorScheduleDto> updateSchedule(Long id, DoctorScheduleDto newData);

    Optional<DoctorScheduleDto> patchSchedule(Long id, Map<String, Object> updates); // Added for PATCH
    boolean deleteSchedule(Long id);
}