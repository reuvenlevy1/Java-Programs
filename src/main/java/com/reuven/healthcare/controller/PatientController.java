package com.reuven.healthcare.controller;

import com.reuven.healthcare.entity.Patient;
import com.reuven.healthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class exposes REST API endpoints to interact with Patient data.
 * Each method corresponds to a standard HTTP operation (GET, POST, PUT, DELETE).
 */

@RestController
@RequestMapping("/api/patients") // Base URL for all endpoints in this controller
public class PatientController {

	private final PatientService patientService;

    @Autowired // Automatically injects the PatientService bean
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * GET /api/patients
     * Returns all patients in the system.
     */
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    /**
     * GET /api/patients/{id}
     * Returns a single patient by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/patients
     * Adds a new patient to the database.
     * The request body must be JSON representing a Patient object.
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    /**
     * PUT /api/patients/{id}
     * Updates an existing patient by ID.
     * If the patient doesn't exist, returns 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Optional<Patient> existingPatient = patientService.getPatientById(id);

        if (existingPatient.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Keep the same ID for the updated record
        updatedPatient.setId(id);
        Patient savedPatient = patientService.savePatient(updatedPatient);
        return ResponseEntity.ok(savedPatient);
    }

    /**
     * DELETE /api/patients/{id}
     * Deletes a patient by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}