package com.reuven.healthcare.controller;

import com.reuven.healthcare.entity.Doctor;
import com.reuven.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class exposes REST API endpoints to interact with Doctor data.
 * Each method corresponds to a standard HTTP operation (GET, POST, PUT, DELETE).
 */

@RestController
@RequestMapping("/api/doctors") // Base URL for all endpoints in this controller
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired // Automatically injects the DoctorService bean
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * POST /api/doctors
     * Adds a new doctor to the database
     * The request body must be JSON representing a Doctor object
     */
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    /**
     * GET /api/doctors
     * Returns all doctors in the system
     */
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    /**
     * GET /api/doctors/{id}
     * Returns a single doctor by ID
     */
    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    /**
     * PUT /api/doctors/{id}
     * Updates an existing doctor by ID
     * If the doctor doesn't exist, returns 404 (Not Found)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor updatedDoctor) {
        Optional<Doctor> existingDoctor = doctorService.getDoctorById(id);

        if (existingDoctor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Keep the same ID for the updated record
        updatedDoctor.setId(id);
        Doctor savedDoctor = doctorService.saveDoctor(updatedDoctor);
        return ResponseEntity.ok(savedDoctor);
    }

    /**
     * DELETE /api/doctors/{id}
     * Deletes a doctor by ID
     */
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
}
