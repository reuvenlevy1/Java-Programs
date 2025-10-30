package com.reuven.healthcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.reuven.healthcare.entity.Doctor;
import com.reuven.healthcare.repository.DoctorRepository;

/**
 * This class contains business logic related to doctors.
 */
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    
    @Autowired // Automatically injects the DoctorRepository bean
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    
    /**
     * Get all doctors from the database
     * 
     * @return List of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Get a specific doctor by ID
     * 
     * @param id - doctor ID
     * @return Optional containing the doctor if found
     */
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    
    /**
     * Add or update a doctor
     * If ID exists, update record
     * If ID doesn’t exist, insert new record
     */
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /**
     * Delete a doctor by ID
     */
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
