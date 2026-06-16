package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorScheduleService {
    DoctorSchedule createSchedule(DoctorSchedule schedule);
    List<DoctorSchedule> getAllSchedules();
    Optional<DoctorSchedule> getScheduleById(Long id);
    List<DoctorSchedule> getSchedulesByDoctorId(Integer doctorID);
    Optional<DoctorSchedule> updateSchedule(Long id, DoctorSchedule newData);
    Optional<DoctorSchedule> patchSchedule(Long id, Map<String, Object> updates); // Added for PATCH
    boolean deleteSchedule(Long id);
}