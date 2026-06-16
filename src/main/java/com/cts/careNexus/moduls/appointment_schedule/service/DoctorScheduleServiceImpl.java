package com.cts.careNexus.moduls.appointment_schedule.service;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.moduls.appointment_schedule.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    @Autowired
    private DoctorScheduleRepository scheduleRepository;

    @Override
    public DoctorSchedule createSchedule(DoctorSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<DoctorSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<DoctorSchedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<DoctorSchedule> getSchedulesByDoctorId(Integer doctorID) {
        return scheduleRepository.findByDoctorID(doctorID);
    }

    @Override
    public Optional<DoctorSchedule> updateSchedule(Long id, DoctorSchedule newData) {
        return scheduleRepository.findById(id).map(existing -> {
            existing.setDoctorID(newData.getDoctorID());
            existing.setDate(newData.getDate());
            existing.setStartTime(newData.getStartTime());
            existing.setEndTime(newData.getEndTime());
            existing.setSlotDurationMinutes(newData.getSlotDurationMinutes());
            existing.setAvailableSlots(newData.getAvailableSlots());
            return scheduleRepository.save(existing);
        });
    }

    @Override
    public Optional<DoctorSchedule> patchSchedule(Long id, Map<String, Object> updates) {
        return scheduleRepository.findById(id).map(existing -> {

            if (updates.containsKey("doctorID")) {
                existing.setDoctorID((Integer) updates.get("doctorID"));
            }
            if (updates.containsKey("date")) {
                String dateStr = (String) updates.get("date");
                existing.setDate(dateStr != null ? LocalDate.parse(dateStr) : null);
            }
            if (updates.containsKey("startTime")) {
                String startTimeStr = (String) updates.get("startTime");
                existing.setStartTime(startTimeStr != null ? LocalTime.parse(startTimeStr) : null);
            }
            if (updates.containsKey("endTime")) {
                String endTimeStr = (String) updates.get("endTime");
                existing.setEndTime(endTimeStr != null ? LocalTime.parse(endTimeStr) : null);
            }
            if (updates.containsKey("slotDurationMinutes")) {
                existing.setSlotDurationMinutes((Integer) updates.get("slotDurationMinutes"));
            }
            if (updates.containsKey("availableSlots")) {
                existing.setAvailableSlots((Integer) updates.get("availableSlots"));
            }

            return scheduleRepository.save(existing);
        });
    }

    @Override
    public boolean deleteSchedule(Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}