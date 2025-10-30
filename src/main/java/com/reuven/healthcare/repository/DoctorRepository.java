package com.reuven.healthcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.reuven.healthcare.entity.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // JpaRepository inherently provides:
    // - save()        : insert or update a doctor
    // - findById()    : get a patient by ID
    // - findAll()     : list all patients
    // - deleteById()  : remove a patient by ID
}
