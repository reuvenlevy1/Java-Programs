package com.reuven.healthcare.service;

import com.reuven.healthcare.entity.Patient;
import com.reuven.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class contains business logic related to patients.
 */
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    
    @Autowired // Automatically injects the PatientRepository bean
    public PatientService(PatientRepository patientRepository) {
    	this.patientRepository = patientRepository;
    }

    /**
     * Get all patients from the database
     * 
     * @return List of all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Get a specific patient by ID
     * 
     * @param id - patient ID
     * @return Optional containing the patient if found
     */
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    /**
     * Add or update a patient
     * If ID exists, update record
     * If ID doesn’t exist, insert new record
     */
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Delete a patient by ID
     */
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
