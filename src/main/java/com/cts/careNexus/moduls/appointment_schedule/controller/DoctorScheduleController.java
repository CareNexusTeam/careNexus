package com.cts.careNexus.moduls.appointment_schedule.controller;

import com.cts.careNexus.moduls.appointment_schedule.dto.DoctorScheduleDto;
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
    public ResponseEntity<DoctorScheduleDto> createSchedule(@RequestBody DoctorScheduleDto scheduleDTO) {
        DoctorScheduleDto savedSchedule = doctorScheduleService.createSchedule(scheduleDTO);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorScheduleDto>> getAllSchedules() {
        List<DoctorScheduleDto> schedules = doctorScheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorScheduleDto> getScheduleById(@PathVariable("id") Long id) {
        return doctorScheduleService.getScheduleById(id)
                .map(schedule -> new ResponseEntity<>(schedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorScheduleDto>> getSchedulesByDoctorId(
            @PathVariable("doctorId") Integer doctorId) {

        List<DoctorScheduleDto> schedules =
                doctorScheduleService.getSchedulesByDoctorId(doctorId);

        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorScheduleDto> updateSchedule(
            @PathVariable("id") Long id,
            @RequestBody DoctorScheduleDto scheduleDTO) {

        return doctorScheduleService.updateSchedule(id, scheduleDTO)
                .map(updatedSchedule -> new ResponseEntity<>(updatedSchedule, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorScheduleDto> patchSchedule(
            @PathVariable("id") Long id,
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
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}