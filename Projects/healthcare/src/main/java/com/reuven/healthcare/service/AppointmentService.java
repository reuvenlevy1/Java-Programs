package com.reuven.healthcare.service;

import com.reuven.healthcare.entity.Appointment;
import com.reuven.healthcare.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This class contains business logic related to appointments.
 * Ensures that no two appointments overlap for the same doctor or patient.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired // Automatically injects the AppointmentRepository bean
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    
    /**
     * Get all appointments from the database.
     *
     * @return List of all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Get a specific appointment by ID.
     *
     * @param id - appointment ID.
     * @return Optional containing the appointment if found.
     */
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    /**
     * Add or update an appointment after validating:
     * 1. Start time is before end time.
     * 2. No overlapping appointments exist for the same doctor or patient.
     *
     * @param appointment The appointment to be saved.
     * @return The saved appointment.
     * @throws IllegalArgumentException if time is invalid or overlaps occur.
     */
    public Appointment saveAppointment(Appointment appointment) {
        // Validate time range
        if (appointment.getStartTime().isAfter(appointment.getEndTime()) ||
            appointment.getStartTime().isEqual(appointment.getEndTime())) {
            throw new IllegalArgumentException("error: Start time must be before end time.");
        }

        // Check for scheduling conflicts
        if (isOverlapping(appointment)) {
            throw new IllegalArgumentException("error: Appointment overlaps with an existing appointment for this doctor or patient.");
        }

        return appointmentRepository.save(appointment);
    }

    /**
     * Checks whether the given appointment overlaps with any existing
     * appointments for the same doctor or patient.
     *
     * Uses optimized repository queries to avoid scanning all appointments.
     *
     * @param newAppointment The appointment to validate.
     * @return true if a conflict exists; false otherwise.
     */
    private boolean isOverlapping(Appointment newAppointment) {
        Long doctorId = newAppointment.getDoctor().getId();
        Long patientId = newAppointment.getPatient().getId();
        LocalDateTime start = newAppointment.getStartTime();
        LocalDateTime end = newAppointment.getEndTime();

        boolean doctorConflict = !appointmentRepository
            .findOverlappingAppointmentsForDoctor(doctorId, start, end)
            .isEmpty();

        boolean patientConflict = !appointmentRepository
            .findOverlappingAppointmentsForPatient(patientId, start, end)
            .isEmpty();

        return doctorConflict || patientConflict;
    }

    /**
     * Updates an existing appointment after validating:
     * 1. Appointment exists.
     * 2. Updated time does not conflict with other appointments.
     *
     * @param id The ID of the appointment to update.
     * @param updated The updated appointment data.
     * @return The saved appointment.
     * @throws IllegalArgumentException if not found or overlaps occur.
     */
    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("error: Appointment not found"));

        // Apply updates
        existing.setDoctor(updated.getDoctor());
        existing.setPatient(updated.getPatient());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        existing.setReason(updated.getReason());
        existing.setNotes(updated.getNotes());

        // Validate updated time range
        if (existing.getStartTime().isAfter(existing.getEndTime()) ||
            existing.getStartTime().isEqual(existing.getEndTime())) {
            throw new IllegalArgumentException("error: Start time must be before end time.");
        }

        // Check for scheduling conflicts
        if (isOverlapping(existing)) {
            throw new IllegalArgumentException("error: Updated appointment overlaps with an existing appointment.");
        }

        return appointmentRepository.save(existing);
    }

    /**
     * Delete an appointment by ID.
     *
     * @param id The appointment ID.
     */
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
