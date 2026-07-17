package com.cts.careNexus.appointment_schedule.service;

import com.cts.careNexus.appointment_schedule.dto.DoctorScheduleDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorScheduleService {

    DoctorScheduleDTO createSchedule(DoctorScheduleDTO dto);

    List<DoctorScheduleDTO> getAllSchedules();

    Optional<DoctorScheduleDTO> getScheduleById(Long id);

    List<DoctorScheduleDTO> getSchedulesByDoctorId(Long doctorId);

    DoctorScheduleDTO updateSchedule(Long id,
                                     DoctorScheduleDTO dto);

    DoctorScheduleDTO patchSchedule(Long id,
                                    Map<String, Object> updates);

    void deleteSchedule(Long id);
}