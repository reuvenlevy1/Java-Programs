package com.reuven.healthcare.repository;

import com.reuven.healthcare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PatientRepository
 * 
 * This interface handles database operations for the Patient entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // JpaRepository inherently provides:
    // - save()        : insert or update a patient
    // - findById()    : get a patient by ID
    // - findAll()     : list all patients
    // - deleteById()  : remove a patient by ID
}
