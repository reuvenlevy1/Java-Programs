package com.reuven.healthcare.controller;

import com.reuven.healthcare.entity.Appointment;
import com.reuven.healthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class exposes REST API endpoints to interact with Appointment data.
 * Each method corresponds to a standard HTTP operation (GET, POST, PUT, DELETE).
 * Includes logic to prevent overlapping appointments for doctors and patients.
 */

@RestController
@RequestMapping("/api/appointments") // Base URL for all endpoints in this controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired // Automatically injects the AppointmentService bean
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * POST /api/appointments
     * Creates a new appointment.
     * The request body must be JSON representing an Appointment object.
     * Throws an error if the appointment overlaps with an existing one.
     */
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    /**
     * GET /api/appointments
     * Returns all appointments in the system.
     */
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    /**
     * GET /api/appointments/{id}
     * Returns a single appointment by ID.
     */
    @GetMapping("/{id}")
    public Optional<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    /**
     * PUT /api/appointments/{id}
     * Updates an existing appointment by ID.
     * If the appointment doesn't exist, returns 404 (Not Found).
     * Throws an error if the updated appointment overlaps with another.
     */    
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        try {
            Appointment saved = appointmentService.updateAppointment(id, updatedAppointment);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * DELETE /api/appointments/{id}
     * Deletes an appointment by ID.
     */
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }
}
