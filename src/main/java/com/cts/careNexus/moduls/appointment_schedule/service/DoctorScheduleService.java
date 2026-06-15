package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleService {
    DoctorSchedule createSchedule(DoctorSchedule schedule);
    List<DoctorSchedule> getAllSchedules();
    Optional<DoctorSchedule> getScheduleById(Long id);
    List<DoctorSchedule> getSchedulesByDoctorId(Integer doctorID);
    Optional<DoctorSchedule> updateSchedule(Long id, DoctorSchedule newData);
    boolean deleteSchedule(Long id);
}