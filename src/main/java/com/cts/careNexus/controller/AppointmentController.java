package com.cts.careNexus.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.careNexus.repository.AppointmentRepository;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository repo;

    @GetMapping
    public Object getAllAppointments() {
        return repo.findAll();
    }
}
