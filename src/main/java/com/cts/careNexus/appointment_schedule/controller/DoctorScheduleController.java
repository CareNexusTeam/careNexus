package com.cts.careNexus.appointment_schedule.controller;

import com.cts.careNexus.appointment_schedule.dto.DoctorScheduleDTO;
import com.cts.careNexus.exception.ResourceNotFoundException;
import com.cts.careNexus.appointment_schedule.service.DoctorScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctor-schedules")
@Validated
public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    public DoctorScheduleController(
            DoctorScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create")
    public ResponseEntity<DoctorScheduleDTO> createSchedule(
            @Valid @RequestBody DoctorScheduleDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(scheduleService.createSchedule(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorScheduleDTO>> getAllSchedules() {

        return ResponseEntity.ok(
                scheduleService.getAllSchedules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorScheduleDTO> getScheduleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                scheduleService.getScheduleById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found with id : " + id)));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorScheduleDTO>>
    getSchedulesByDoctorId(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                scheduleService.getSchedulesByDoctorId(doctorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorScheduleDTO> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody DoctorScheduleDTO dto) {

        return ResponseEntity.ok(
                scheduleService.updateSchedule(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorScheduleDTO> patchSchedule(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        return ResponseEntity.ok(
                scheduleService.patchSchedule(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long id) {

        scheduleService.deleteSchedule(id);

        return ResponseEntity.ok(
                "Schedule deleted successfully");
    }
}