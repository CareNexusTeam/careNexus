package com.cts.careNexus.moduls.appointment_schedule.controller;

import com.cts.careNexus.moduls.appointment_schedule.entity.DoctorSchedule;
import com.cts.careNexus.moduls.appointment_schedule.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class DoctorScheduleController {

    @Autowired
    private DoctorScheduleService doctorScheduleService;

    @PostMapping
    public ResponseEntity<DoctorSchedule> createSchedule(@RequestBody DoctorSchedule schedule) {
        DoctorSchedule savedSchedule = doctorScheduleService.createSchedule(schedule);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorSchedule>> getAllSchedules() {
        List<DoctorSchedule> schedules = doctorScheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable("id") Long id) {
        return doctorScheduleService.getScheduleById(id)
                .map(schedule -> new ResponseEntity<>(schedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorSchedule>> getSchedulesByDoctorId(@PathVariable("doctorId") Integer doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleService.getSchedulesByDoctorId(doctorId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorSchedule> updateSchedule(@PathVariable("id") Long id,
                                                         @RequestBody DoctorSchedule scheduleDetails) {
        return doctorScheduleService.updateSchedule(id, scheduleDetails)
                .map(updatedSchedule -> new ResponseEntity<>(updatedSchedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorSchedule> patchSchedule(@PathVariable("id") Long id,
                                                        @RequestBody Map<String, Object> updates) {
        return doctorScheduleService.patchSchedule(id, updates)
                .map(patchedSchedule -> new ResponseEntity<>(patchedSchedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long id) {
        try {
            boolean isDeleted = doctorScheduleService.deleteSchedule(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}